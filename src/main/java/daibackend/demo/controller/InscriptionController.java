package daibackend.demo.controller;


import daibackend.demo.model.*;
import daibackend.demo.model.custom.*;
import daibackend.demo.payload.response.ApiResponse;
import daibackend.demo.repository.ActivityRepository;
import daibackend.demo.repository.ChildRepository;
import daibackend.demo.repository.InscriptionRepository;
import daibackend.demo.security.CurrentUser;
import daibackend.demo.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping(value = "/api")
public class InscriptionController {

    @Autowired
    ChildRepository childRepository;

    @Autowired
    InscriptionRepository inscriptionRepository;

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(
            String to, String subject, String text) throws MessagingException {

        SimpleMailMessage message = new SimpleMailMessage();

        MimeMessage mailMessage = emailSender.createMimeMessage();

        mailMessage.setSubject(subject, "UTF-8");

        MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true, "UTF-8");
        helper.setFrom("noreply@baeldung.com");
        helper.setTo(to);
        helper.setText(text, true);

       /* message.setFrom("noreply@baeldung.com");
        message.setTo(to);
        message.setText(text);*/
        emailSender.send(mailMessage);

    }

    //@PreAuthorize("hasRole('ADMINISTRATOR')or hasROLE('CHILD')")
    @GetMapping("/children/{idChild}/activities")
    public List<InscriptionActivitiesByChildList> listActivitiesByChild(/*@CurrentUser UserPrincipal currentUser*/ @PathVariable long idChild) {
        Child child = childRepository.findDistinctByIdChild(idChild);
        return inscriptionRepository.findAllByActivityChild(idChild,1,1);
    }

    @GetMapping("/children/{idChild}/currentactivities")
    public List<InscriptionActivitiesByChildList> listCurrentActivitiesByChild(/*@CurrentUser UserPrincipal currentUser*/ @PathVariable long idChild) {
        Child child = childRepository.findDistinctByIdChild(idChild);
        return inscriptionRepository.findAllByCurrentActivityChild(idChild,"Aprovada",1);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('INSTITUTION')")
    @GetMapping("/activities/{idActivity}/children")
    public List<InscriptionChildrenByActivityList> listChildrenByActivity(/*@CurrentUser UserPrincipal currentUser*/ @PathVariable long idActivity) {

        return inscriptionRepository.findAllByChildActivity(idActivity,1);
    }

    //@PreAuthorize("hasROLE('CHILD')")
    @PostMapping("/activities/{idActivity}/children") // Creat inscription
    public ResponseEntity<ApiResponse> saveInscription(@CurrentUser UserPrincipal currentUser, @PathVariable long idActivity, @RequestBody CreateInscription inscription) {
        try {
            long idChild = inscription.getIdChild();
            Child child = childRepository.findDistinctByIdChild(idChild);
           // System.out.println(currentUser);
            if(child.getAge()>=13) {
                if (childRepository.findDistinctByIdChild(idChild).equals(null)) {
                    return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Criança não existe."),
                            HttpStatus.BAD_REQUEST);
                }

                if (!activityRepository.findByIdActivity(idActivity).getStatus().equals("Aprovada")) {
                    return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Inscrição inválida."),
                            HttpStatus.BAD_REQUEST);
                }
                Inscription I = new Inscription();
                I.setChild(childRepository.findDistinctByIdChild(idChild));
                I.setActivity(activityRepository.findByIdActivity(idActivity));
                inscriptionRepository.saveInscription(idChild, idActivity, I.getPresence(), I.getEvaluation(),1,0);

                return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Inscription created", I.getChild().getIdChild()),
                        HttpStatus.CREATED);

            } else {

                Random rnd = new Random();
                int number = rnd.nextInt(900000)  + 100000;
                Inscription I = new Inscription();
                inscriptionRepository.saveInscription(idChild,idActivity,I.getPresence(),I.getEvaluation(),0,number);
                String message="";
                message+="<p>Caro encarregado,</p><br><p>O seu educando "+child.getName()+" inscreveu-se na atividade \"+ activityRepository.findByIdActivity(idActivity).getName()+\" que decorrerá em \"+ activityRepository.findByIdActivity(idActivity).getAddress() +\", na seguinte data, \"+activityRepository.findByIdActivity(idActivity).getInit_data().toString()+\"</p><br> \"Para ativar a sua inscrição, clique no seguinte link:  <br>  <a href='http://127.0.0.1:8080/api/activities/"+idActivity+"/children/"+idChild+"/active?activate="+number+"'>Clique Aqui!<a/>   <br><p>Com os melhores cumprimentos,<br>ProChildColab</p><br>";
                //String message = "Caro encarregado,"+System.lineSeparator()+ System.lineSeparator()+"O seu educando "+child.getName()+" inscreveu-se na atividade "+ activityRepository.findByIdActivity(idActivity).getName()+" que decorrerá em "+ activityRepository.findByIdActivity(idActivity).getAddress() +", na seguinte data, "+activityRepository.findByIdActivity(idActivity).getInit_data().toString()+"." + System.lineSeparator()+ System.lineSeparator()+"Para ativar a sua inscrição, o seguinte código foi gerado: " +String.valueOf(number)+ System.lineSeparator() +System.lineSeparator()+"Com os melhores cumprimentos,"+System.lineSeparator()+"ProChildColab";
                sendSimpleMessage(childRepository.findDistinctByIdChild(idChild).getParentEmail(),"City4kids- Código de ativação de inscrição", message);
                return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Inscription created", idChild),
                        HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                    HttpStatus.BAD_REQUEST);
        }
    }
    //@PreAuthorize("hasROLE('CHILD')")  // Child
    @PutMapping("/activities/{idActivity}/children/{idChild}")
    public ResponseEntity<ApiResponse> updateInscriptionEvaluation(@PathVariable (value="idChild")long idChild,@PathVariable (value="idActivity")long idActivity, @RequestBody updateInt update) {
        try {
            if (childRepository.findDistinctByIdChild(idChild).equals(null) || activityRepository.findByIdActivity(idActivity).equals(null)) {
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                        HttpStatus.BAD_REQUEST);
            }

            if (update.getInt() != 1 && update.getInt() != 2 && update.getInt() != 3 && update.getInt() != 4 && update.getInt() != 5) {
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Evaluation invalid."),
                        HttpStatus.BAD_REQUEST);
            }
            inscriptionRepository.updateEvaluation(update.getInt(), idChild, idActivity);

            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Evaluation updated.", idChild),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                    HttpStatus.BAD_REQUEST);
        }
    }

   // @PreAuthorize("hasRole('ADMINISTRATOR')or hasROLE('CHILD')")  // Child
    @GetMapping("/activities/{idActivity}/children/{idChild}/active")
    public ResponseEntity<ApiResponse> updateInscriptionActive(@PathVariable (value="idChild")long idChild,@PathVariable (value="idActivity")long idActivity, @RequestParam int activate) {
        try {
            if (childRepository.findDistinctByIdChild(idChild).equals(null) || activityRepository.findByIdActivity(idActivity).equals(null)) {
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                        HttpStatus.BAD_REQUEST);
            }
            inscriptionRepository.updateActive(1,idChild,idActivity,activate);

            Inscription inscription = inscriptionRepository.findDistinctByActivityAndChild(activityRepository.findByIdActivity(idActivity),childRepository.findDistinctByIdChild(idChild));
            if(inscription.getActive()==1){
                return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Inscription activated.", idChild),
                        HttpStatus.CREATED);
            }else {
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Inscription not activated.", idChild),
                        HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('INSTITUTION') ")  // Child
        @PutMapping("/activities/{idActivity}/children/{idChild}/presence")
        public ResponseEntity<ApiResponse> updateInscriptionpresence(@PathVariable (value="idChild")long idChild,@PathVariable (value="idActivity")long idActivity, @RequestBody updateInt update) {
            try {
                if(childRepository.findDistinctByIdChild(idChild).equals(null)|| activityRepository.findByIdActivity(idActivity).equals(null)){
                    return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                            HttpStatus.BAD_REQUEST);
                }

                if(update.getInt()!=1 && update.getInt()!=0){
                    return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Presence invalid."),
                            HttpStatus.BAD_REQUEST);
                }
                System.out.println(update);
                inscriptionRepository.updatePresence(update.getInt(),idChild,idActivity);

                return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Presence updated.", idChild),
                        HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                        HttpStatus.BAD_REQUEST);
            }
    }
   // @PreAuthorize("hasRole('ADMINISTRATOR') or hasROLE('CHILD')")
    @DeleteMapping("/activities/{idActivity}/children/{idChild}")
    public ResponseEntity<ApiResponse> deleteInscription(@PathVariable (value="idChild")long idChild,@PathVariable (value="idActivity")long idActivity) {
        try {
            inscriptionRepository.deleteInscriptionByActivityAndChild(idActivity,idChild);

            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Inscription deleted.", idChild),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMINISTRATOR') or hasROLE('CHILD')")
    @GetMapping("/ranks")
    public List<RankList> listRanks(/*@CurrentUser UserPrincipal currentUser*/) {

        return inscriptionRepository.findRankByPoints(1);
    }
}
