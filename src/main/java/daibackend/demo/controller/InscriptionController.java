package daibackend.demo.controller;


import daibackend.demo.model.Child;
import daibackend.demo.model.Login;
import daibackend.demo.model.Role;
import daibackend.demo.model.custom.CreateChild;
import daibackend.demo.model.custom.InscriptionActivitiesByChildList;
import daibackend.demo.model.custom.InscriptionChildrenByActivityList;
import daibackend.demo.payload.response.ApiResponse;
import daibackend.demo.repository.ChildRepository;
import daibackend.demo.repository.InscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class InscriptionController {

    @Autowired
    ChildRepository childRepository;

    @Autowired
    InscriptionRepository inscriptionRepository;

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
    //@PostMapping("/activities/{idActivity}/children") // Creat inscription
    //public ResponseEntity<ApiResponse> saveChild(@RequestBody CreateChild child) {
       // try {
            // Activity Attributes
           /*
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

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(password);
            // Create Login
            Login l = new Login(null,email,hashedPassword,role);
            loginRepository.save(l);

            // Create Child
            Child newChild = new Child(null,l,name,age,address);
            childRepository.save(newChild);

            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Account created",loginRepository.findDistinctByEmail(email).getIdLogin()),
                    HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                    HttpStatus.BAD_REQUEST);
                     */

       // }


   // }
}
