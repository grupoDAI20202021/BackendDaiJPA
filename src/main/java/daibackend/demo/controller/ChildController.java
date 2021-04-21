package daibackend.demo.controller;

import daibackend.demo.model.*;
import daibackend.demo.model.custom.CreateChild;

import daibackend.demo.model.custom.updateEmail;
import daibackend.demo.model.custom.updateInt;
import daibackend.demo.model.custom.updatePassword;
import daibackend.demo.payload.response.ApiResponse;
import daibackend.demo.repository.ChildRepository;
import daibackend.demo.repository.LoginRepository;
import daibackend.demo.security.CurrentUser;
import daibackend.demo.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ChildController {
    @Autowired
    ChildRepository childRepository;

    @Autowired
    LoginRepository loginRepository;

    @PreAuthorize("hasRole('INSTITUTION') or hasRole('MANAGER') or hasRole('NETWORKMAN')")
    @GetMapping("/children")
    public List<Child> listChildren(@CurrentUser UserPrincipal currentUser) {
        //User userLogged = userRepository.findByUserId(currentUser.getId());
        //Set<Role> roleUserLogged = userLogged.getRoles();

        // Get Permissions
        /*if (String.valueOf(roleUserLogged).equals("[Role [id=0]]")
                || String.valueOf(roleUserLogged).equals("[Role [id=1]]")) {
            return alertLogRepository.findAlertLogsByPrison(userLogged.getPrison());
        }*/
        return childRepository.findAll();
    }

    //@PreAuthorize("hasRole('GUARD') or hasRole('MANAGER') or hasRole('NETWORKMAN')")
    @GetMapping("/children/{idChild}")
    public Child findChild(/*@CurrentUser UserPrincipal currentUser*/ @PathVariable long idChild) {
        //User userLogged = userRepository.findByUserId(currentUser.getId());
        //Set<Role> roleUserLogged = userLogged.getRoles();

        // Get Permissions
        /*if (String.valueOf(roleUserLogged).equals("[Role [id=0]]")
                || String.valueOf(roleUserLogged).equals("[Role [id=1]]")) {
            return alertLogRepository.findAlertLogsByPrison(userLogged.getPrison());
        }*/
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
                Login l = new Login(null,email,hashedPassword,role);
            Child newChild = new Child(null,l,name,age,address,contact,idAvatar);
                loginRepository.save(l);

                // Create Child
                childRepository.save(newChild);

                return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Account created",loginRepository.findDistinctByEmail(email).getIdLogin()),
                        HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                    HttpStatus.BAD_REQUEST);
        }
    }
    //@PreAuthorize("hasRole('GUARD') or hasRole('MANAGER') or hasRole('NETWORKMAN')")  // Child
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
