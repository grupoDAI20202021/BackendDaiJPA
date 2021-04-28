package daibackend.demo.controller;

import daibackend.demo.model.*;
import daibackend.demo.model.custom.*;
import daibackend.demo.payload.response.ApiResponse;
import daibackend.demo.repository.ChildRepository;
import daibackend.demo.repository.InstitutionRepository;
import daibackend.demo.repository.LoginRepository;
import daibackend.demo.repository.TownHallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class InstitutionController {

    @Autowired
    InstitutionRepository institutionRepository;

    @Autowired
    LoginRepository loginRepository;

    @Autowired
    TownHallRepository townHallRepository;

    //@PreAuthorize("hasRole('GUARD') or hasRole('MANAGER') or hasRole('NETWORKMAN')")
    @GetMapping("/institutions")
    public List<InstitutionList> listInstitutions(/*@CurrentUser UserPrincipal currentUser*/) {
        //User userLogged = userRepository.findByUserId(currentUser.getId());
        //Set<Role> roleUserLogged = userLogged.getRoles();

        // Get Permissions
        /*if (String.valueOf(roleUserLogged).equals("[Role [id=0]]")
                || String.valueOf(roleUserLogged).equals("[Role [id=1]]")) {
            return alertLogRepository.findAlertLogsByPrison(userLogged.getPrison());
        }*/
        return institutionRepository.findAllInstitution(1);
    }
    //@PreAuthorize("hasRole('GUARD') or hasRole('MANAGER') or hasRole('NETWORKMAN')")
    @GetMapping("/institutions/{idInstitution}")
    public Institution findInstitution(/*@CurrentUser UserPrincipal currentUser*/ @PathVariable long idInstitution) {
        //User userLogged = userRepository.findByUserId(currentUser.getId());
        //Set<Role> roleUserLogged = userLogged.getRoles();

        // Get Permissions
        /*if (String.valueOf(roleUserLogged).equals("[Role [id=0]]")
                || String.valueOf(roleUserLogged).equals("[Role [id=1]]")) {
            return alertLogRepository.findAlertLogsByPrison(userLogged.getPrison());
        }*/
        return institutionRepository.findDistinctByIdInstitution(idInstitution);
    }

    //@PreAuthorize("hasRole('GUARD') or hasRole('MANAGER') or hasRole('NETWORKMAN')")
    @GetMapping("/institutions/{idInstitution}/townhall")
    public long findTownHallByIntitution(/*@CurrentUser UserPrincipal currentUser*/ @PathVariable long idInstitution) {
        //User userLogged = userRepository.findByUserId(currentUser.getId());
        //Set<Role> roleUserLogged = userLogged.getRoles();

        // Get Permissions
        /*if (String.valueOf(roleUserLogged).equals("[Role [id=0]]")
                || String.valueOf(roleUserLogged).equals("[Role [id=1]]")) {
            return alertLogRepository.findAlertLogsByPrison(userLogged.getPrison());
        }*/
        return institutionRepository.getTownhallId(idInstitution);
    }

    @PostMapping("/institutions") // Creat account
    public ResponseEntity<ApiResponse> saveInstitution(@RequestBody CreateInstitution institution) {
        try {
            // Activity Attributes
            String email  = institution.getEmail();
            String password  = institution.getPassword();
            String confirmPassword = institution.getConfirmPassword();
            String name= institution.getName();
            Role role = institution.getRole();
            String address = institution.getAddress();
            long idTownHall = institution.getIdTownHall();
            TownHall townHall = townHallRepository.findDistinctByIdTownHall(idTownHall);

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
            if (!townHallRepository.existsById(idTownHall)) {
                return new ResponseEntity<ApiResponse>(
                        new ApiResponse(false, "TownHall doesn´t exist."),
                        HttpStatus.BAD_REQUEST);
            }
            if(!(role.getIdRole() ==2)){
                return new ResponseEntity<ApiResponse>(
                        new ApiResponse(false, "Role inválido"),
                        HttpStatus.BAD_REQUEST);
            }

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(password);
            // Create Login
            Login l = new Login(null,email,hashedPassword,role,1);
            loginRepository.save(l);

            // Create Institution
            Institution newInstitution = new Institution(null,l,townHall,name,address);
            institutionRepository.save(newInstitution);

            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Account created",loginRepository.findDistinctByEmailAndActive(email,1).getIdLogin()),
                    HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                    HttpStatus.BAD_REQUEST);
        }
    }
    //@PreAuthorize("hasRole('GUARD') or hasRole('MANAGER') or hasRole('NETWORKMAN')")  // Institution
    @PutMapping("/institutions/{idInstitution}/password")
    public ResponseEntity<ApiResponse> updateInstitutionPassword(@PathVariable (value="idInstitution")long idInstitution, @RequestBody updatePassword update) {
        try {
            if (institutionRepository.findDistinctByIdInstitution(idInstitution).equals(null)) {
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                        HttpStatus.BAD_REQUEST);
            }

            String oldPassword = update.getOldPassword();
            String newPassword = update.getPassword();
            String confirmPassword = update.getConfirmPassword();


            if (oldPassword.equals(newPassword)) {
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
            long idLogin = institutionRepository.findDistinctByIdInstitution(idInstitution).getLogin().getIdLogin();

            loginRepository.updateLoginPassword(hashedPassword, idLogin);

            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Password updated.", idInstitution),
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
        @PutMapping("/institutions/{idInstitution}")
        public ResponseEntity<ApiResponse> updateInstitution(@PathVariable (value="idInstitution")long idInstitution, @RequestBody updateEmail update) {
            try {
                if(institutionRepository.findDistinctByIdInstitution(idInstitution).equals(null)){
                    return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                            HttpStatus.BAD_REQUEST);
                }

                String email = update.getEmail();

                if(email.trim().equals("")){
                    return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Email inválido."),
                            HttpStatus.BAD_REQUEST);
                }

                long idLogin = institutionRepository.findDistinctByIdInstitution(idInstitution).getLogin().getIdLogin();

                loginRepository.updateLoginEmail(email,idLogin);

                return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Email updated.", idInstitution),
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
    @DeleteMapping("/institutions/{idInstitution}")
    public ResponseEntity<ApiResponse> deleteInstitution(@PathVariable (value="idInstitution")long idInstitution) {

            Institution institution = institutionRepository.findDistinctByIdInstitution(idInstitution);
            Login login = loginRepository.findDistinctByIdLogin(institution.getLogin().getIdLogin());
            institutionRepository.delete(institution);
            loginRepository.delete(login);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Institution deleted.", idInstitution),
                    HttpStatus.CREATED);

    }
    }

