package daibackend.demo.controller;

import daibackend.demo.model.*;
import daibackend.demo.model.custom.*;

import daibackend.demo.payload.response.ApiResponse;
import daibackend.demo.repository.ChildRepository;
import daibackend.demo.repository.LoginRepository;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping(value = "/api")
public class ChildController {
    @Autowired
    ChildRepository childRepository;

    @Autowired
    LoginRepository loginRepository;

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

    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TOWNHALL') or hasRole('INSTITUTION') or hasROLE('CHILD')")
    @GetMapping("/children")
    public List<Child> listChildren(@CurrentUser UserPrincipal currentUser) {
        return childRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TOWNHALL') or hasRole('INSTITUTION') or hasROLE('CHILD')")
    @GetMapping("/children/{idChild}")
    public Child findChild(/*@CurrentUser UserPrincipal currentUser*/ @PathVariable long idChild) {

        return childRepository.findDistinctByIdChild(idChild);
    }

    @PostMapping("/children") // Creat account
    public ResponseEntity<ApiResponse> saveChild(@RequestBody CreateChild child) {
        try {
            // Activity Attributes
            long idAvatar=child.getIdAvatar();
            String contact = child.getContact();
            String email  = child.getEmail();
            String password  = child.getPassword();
            String confirmPassword = child.getConfirmPassword();
            String name= child.getName();
            Role role = child.getRole();
            int age = child.getAge();
            String address = child.getAddress();
            if (!confirmPassword.equals(password)) {
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Passwords não são iguais."),
                        HttpStatus.BAD_REQUEST);
            }
                if (loginRepository.existsByEmail(email)) {
                    return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Email já em utilização."),
                            HttpStatus.BAD_REQUEST);
                }
            if (String.valueOf(password).length() < 6 || String.valueOf(password).length() > 24) {
                return new ResponseEntity<ApiResponse>(
                        new ApiResponse(false, "Password must contain between 6 to 24 characters"),
                        HttpStatus.BAD_REQUEST);
            }
            if(!(role.getIdRole() ==3)){
                return new ResponseEntity<ApiResponse>(
                        new ApiResponse(false, "Role inválido"),
                        HttpStatus.BAD_REQUEST);
            }

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(password);
                // Create Login

                if(age >= 13) {
                    Login l = new Login(null,email,hashedPassword,role,1);
                    Child newChild = new Child(null, l, name, age, address, contact);
                    loginRepository.save(l);

                    // Create Child
                    childRepository.save(newChild);

                    return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Account created", newChild.getIdChild()),
                            HttpStatus.CREATED);
                }else {

                    String parentEmail= child.getParent_email();
                    Random rnd = new Random();
                    int number = rnd.nextInt(900000)  + 100000;
                    Login l = new Login(null,email,hashedPassword,role,0,number);
                    Child newChild = new Child(null, l, name, age, address, contact,parentEmail);
                    loginRepository.save(l);
                    // Create Child
                    childRepository.save(newChild);
                    //String message = "Caro encarregado,"+System.lineSeparator()+ System.lineSeparator()+"O código de ativação do seu educando é: "+ String.valueOf(l.getGeneratedCode()) + System.lineSeparator() +System.lineSeparator()+"Com os melhores cumprimentos,"+System.lineSeparator()+"ProChildColab";
                    String message="";
                    message+="<p>Caro encarregado,</p><br><p>Para ativar a conta do seu educando carregue no seguinte link:</p><br> \"<a href='http://127.0.0.1:8080/api/login/activate?updateEmail=\"+email+\"&updateGeneratedCode=\"+number+\"'>Clique Aqui!<a/>\"  <br><p>Com os melhores cumprimentos,<br>ProChildColab</p><br>";

                    sendSimpleMessage(newChild.getParentEmail(),"City4kids- Código de ativação de conta do seu educando", message);

                    return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Account created", newChild.getIdChild()),
                            HttpStatus.CREATED);
                }

        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }



    @PreAuthorize("hasRole('CHILD')")  // Child
    @PutMapping("/children/{idChild}/password")
    public ResponseEntity<ApiResponse> updateChildPassword(@PathVariable (value="idChild")long idChild, @RequestBody updatePassword update) {
        try {
            if(childRepository.findDistinctByIdChild(idChild).equals(null)){
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                        HttpStatus.BAD_REQUEST);
            }

            String oldPassword = update.getOldPassword();
            String newPassword = update.getPassword();
            String confirmPassword = update.getConfirmPassword();


            if(oldPassword.equals(newPassword)){
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Passwords repetidas."),
                        HttpStatus.BAD_REQUEST);
            }

            if (!confirmPassword.equals(newPassword)) {
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Passwords não são iguais."),
                        HttpStatus.BAD_REQUEST);
            }
            if (newPassword.length() < 6 || newPassword.length() > 24) {
                return new ResponseEntity<ApiResponse>(
                        new ApiResponse(false, "Password must contain between 6 to 24 characters"),
                        HttpStatus.BAD_REQUEST);
            }

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(newPassword);
            long idLogin = childRepository.findDistinctByIdChild(idChild).getLogin().getIdLogin();

            loginRepository.updateLoginPassword(hashedPassword,idLogin);

            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Password updated.", idChild),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('CHILD')")  // Child
    @PutMapping("/children/{idChild}")
    public ResponseEntity<ApiResponse> updateChild(@PathVariable (value="idChild")long idChild, @RequestBody updateEmail update) {
        try {
            if(childRepository.findDistinctByIdChild(idChild).equals(null)){
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                        HttpStatus.BAD_REQUEST);
            }

            String email = update.getEmail();


            if(email.trim().equals("")){
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Email inválido."),
                        HttpStatus.BAD_REQUEST);
            }

            long idLogin = childRepository.findDistinctByIdChild(idChild).getLogin().getIdLogin();

            loginRepository.updateLoginEmail(email,idLogin);

            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Email updated.", idChild),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('CHILD')")  // Child
    @PutMapping("/children/{idChild}/avatar")
    public ResponseEntity<ApiResponse> updateChildAvatar(@PathVariable (value="idChild")long idChild, @RequestBody updateInt Int) {
        try {
            if(childRepository.findDistinctByIdChild(idChild).equals(null)){
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                        HttpStatus.BAD_REQUEST);
            }

            long idAvatar = Int.getInt();


            childRepository.updateChildAvatar(idAvatar,idChild);

            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Avatar updated.", idChild),
                    HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                    HttpStatus.BAD_REQUEST);
        }
    }
    @PreAuthorize("hasRole ('ADMINISTRATOR') or hasRole('CHILD')")  // Child
    @DeleteMapping("/children/{idChild}")
    public ResponseEntity<ApiResponse> deleteChild(@PathVariable (value="idChild")long idChild) {
        try {
            Child child = childRepository.findDistinctByIdChild(idChild);
            Login login = loginRepository.findDistinctByIdLogin(child.getLogin().getIdLogin());

            childRepository.delete(child);
            loginRepository.delete(login);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Child deleted.", idChild),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
