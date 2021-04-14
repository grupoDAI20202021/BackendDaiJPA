package daibackend.demo.controller;


import daibackend.demo.model.*;
import daibackend.demo.model.custom.*;
import daibackend.demo.payload.response.ApiResponse;
import daibackend.demo.repository.ActivityRepository;
import daibackend.demo.repository.ChildRepository;
import daibackend.demo.repository.InscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class InscriptionController {

    @Autowired
    ChildRepository childRepository;

    @Autowired
    InscriptionRepository inscriptionRepository;

    @Autowired
    ActivityRepository activityRepository;

    //@PreAuthorize("hasRole('GUARD') or hasRole('MANAGER') or hasRole('NETWORKMAN')")
    @GetMapping("/children/{idChild}/activities")
    public List<InscriptionActivitiesByChildList> listActivitiesByChild(/*@CurrentUser UserPrincipal currentUser*/ @PathVariable long idChild) {
        //User userLogged = userRepository.findByUserId(currentUser.getId());
        //Set<Role> roleUserLogged = userLogged.getRoles();

        // Get Permissions
        /*if (String.valueOf(roleUserLogged).equals("[Role [id=0]]")
                || String.valueOf(roleUserLogged).equals("[Role [id=1]]")) {
            return alertLogRepository.findAlertLogsByPrison(userLogged.getPrison());
        }*/
        Child child = childRepository.findDistinctByIdChild(idChild);
        return inscriptionRepository.findAllByActivityChild(idChild,1);
    }

    //@PreAuthorize("hasRole('GUARD') or hasRole('MANAGER') or hasRole('NETWORKMAN')")
    @GetMapping("/activities/{idActivity}/children")
    public List<InscriptionChildrenByActivityList> listChildrenByActivity(/*@CurrentUser UserPrincipal currentUser*/ @PathVariable long idActivity) {
        //User userLogged = userRepository.findByUserId(currentUser.getId());
        //Set<Role> roleUserLogged = userLogged.getRoles();

        // Get Permissions
        /*if (String.valueOf(roleUserLogged).equals("[Role [id=0]]")
                || String.valueOf(roleUserLogged).equals("[Role [id=1]]")) {
            return alertLogRepository.findAlertLogsByPrison(userLogged.getPrison());
        }*/

        return inscriptionRepository.findAllByChildActivity(idActivity);
    }
    @PostMapping("/activities/{idActivity}/children") // Creat inscription
    public ResponseEntity<ApiResponse> saveInscription(@PathVariable long idActivity,@RequestBody CreateInscription inscription) {
        try {
            long idChild = inscription.getIdChild();

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
            inscriptionRepository.saveInscription(idChild,idActivity , I.getPresence(), I.getEvaluation());

            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Inscription created",I.getChild().getIdChild()),
                    HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                    HttpStatus.BAD_REQUEST);
        }
    }
    //@PreAuthorize("hasRole('GUARD') or hasRole('MANAGER') or hasRole('NETWORKMAN')")  // Child
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
            //User userLogged = userRepository.findByUserId(currentUser.getId());
            //Set<Role> roleUserLogged = userLogged.getRoles();

            // Get Permissions
        /*if (String.valueOf(roleUserLogged).equals("[Role [id=0]]")
                || String.valueOf(roleUserLogged).equals("[Role [id=1]]")) {
            return alertLogRepository.findAlertLogsByPrison(userLogged.getPrison());
        }*/
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                    HttpStatus.BAD_REQUEST);
        }
    }
        //@PreAuthorize("hasRole('GUARD') or hasRole('MANAGER') or hasRole('NETWORKMAN')")  // Child
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
                //User userLogged = userRepository.findByUserId(currentUser.getId());
                //Set<Role> roleUserLogged = userLogged.getRoles();

                // Get Permissions
        /*if (String.valueOf(roleUserLogged).equals("[Role [id=0]]")
                || String.valueOf(roleUserLogged).equals("[Role [id=1]]")) {
            return alertLogRepository.findAlertLogsByPrison(userLogged.getPrison());
        }*/
            } catch (Exception e) {
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                        HttpStatus.BAD_REQUEST);
            }
    }

    @DeleteMapping("/activities/{idActivity}/children/{idChild}")
    public ResponseEntity<ApiResponse> deleteInscription(@PathVariable (value="idChild")long idChild,@PathVariable (value="idActivity")long idActivity) {
        try {
            Child child = childRepository.findDistinctByIdChild(idChild);
            Activity activity = activityRepository.findByIdActivity(idActivity);
            inscriptionRepository.deleteInscriptionByActivityAndChild(idActivity,idChild);

            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Inscription deleted.", idChild),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    //@PreAuthorize("hasRole('GUARD') or hasRole('MANAGER') or hasRole('NETWORKMAN')")
    @GetMapping("/ranks")
    public List<RankList> listRanks(/*@CurrentUser UserPrincipal currentUser*/) {
        //User userLogged = userRepository.findByUserId(currentUser.getId());
        //Set<Role> roleUserLogged = userLogged.getRoles();

        // Get Permissions
        /*if (String.valueOf(roleUserLogged).equals("[Role [id=0]]")
                || String.valueOf(roleUserLogged).equals("[Role [id=1]]")) {
            return alertLogRepository.findAlertLogsByPrison(userLogged.getPrison());
        }*/

        return inscriptionRepository.findRankByPoints();
    }
}
