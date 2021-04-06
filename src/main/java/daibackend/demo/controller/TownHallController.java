package daibackend.demo.controller;

import daibackend.demo.model.Institution;
import daibackend.demo.model.Login;
import daibackend.demo.model.Role;
import daibackend.demo.model.TownHall;
import daibackend.demo.model.custom.*;
import daibackend.demo.payload.response.ApiResponse;
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
public class TownHallController {
    @Autowired
    TownHallRepository townHallRepository;

    @Autowired
    LoginRepository loginRepository;

    //@PreAuthorize("hasRole('GUARD') or hasRole('MANAGER') or hasRole('NETWORKMAN')")
    @GetMapping("/townhalls")
    public List<TownHallList> listTownHalls(/*@CurrentUser UserPrincipal currentUser*/) {
        //User userLogged = userRepository.findByUserId(currentUser.getId());
        //Set<Role> roleUserLogged = userLogged.getRoles();

        // Get Permissions
        /*if (String.valueOf(roleUserLogged).equals("[Role [id=0]]")
                || String.valueOf(roleUserLogged).equals("[Role [id=1]]")) {
            return alertLogRepository.findAlertLogsByPrison(userLogged.getPrison());
        }*/
        return townHallRepository.findAllTownHallList();
    }

    //@PreAuthorize("hasRole('GUARD') or hasRole('MANAGER') or hasRole('NETWORKMAN')")
    @GetMapping("/townhalls/{idTownHall}")
    public TownHall getTownHall(/*@CurrentUser UserPrincipal currentUser*/@PathVariable long idTownHall) {

        //User userLogged = userRepository.findByUserId(currentUser.getId());
        //Set<Role> roleUserLogged = userLogged.getRoles();

        // Get Permissions
        /*if (String.valueOf(roleUserLogged).equals("[Role [id=0]]")
                || String.valueOf(roleUserLogged).equals("[Role [id=1]]")) {
            return alertLogRepository.findAlertLogsByPrison(userLogged.getPrison());
        }*/
        return townHallRepository.findDistinctByIdTownHall(idTownHall);
    }

    @PostMapping("/townhalls") // Creat account
    public ResponseEntity<ApiResponse> saveTownHall(@RequestBody CreateTownHall createTownHall) {
        try {
            // Activity Attributes
            String email  = createTownHall.getEmail();
            String password  = createTownHall.getPassword();
            String confirmPassword = createTownHall.getConfirmPassword();
            String name= createTownHall.getName();
            Role role = createTownHall.getRole();
            String address = createTownHall.getAddress();

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
            if(!(role.getIdRole() ==1)){
                return new ResponseEntity<ApiResponse>(
                        new ApiResponse(false, "Role inválido"),
                        HttpStatus.BAD_REQUEST);
            }
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(password);
            // Create Login
            Login l = new Login(null,email,hashedPassword,role);
            loginRepository.save(l);

            // Create Institution
            TownHall townHall = new TownHall(null,l,name,address);
            townHallRepository.save(townHall);

            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Account created",loginRepository.findDistinctByEmail(email).getIdLogin()),
                    HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    //@PreAuthorize("hasRole('GUARD') or hasRole('MANAGER') or hasRole('NETWORKMAN')")  // Institution
    @PutMapping("/townhalls/{idTownHall}/password")
    public ResponseEntity<ApiResponse> updateTownHallPassword(@PathVariable (value="idTownHall")long idTownHall, @RequestBody updatePassword update) {
        try {
            if (townHallRepository.findDistinctByIdTownHall(idTownHall).equals(null)) {
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
            long idLogin = townHallRepository.findDistinctByIdTownHall(idTownHall).getLogin().getIdLogin();

            loginRepository.updateLoginPassword(hashedPassword, idLogin);

            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Password updated.", idTownHall),
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
    @PutMapping("/townhalls/{idTownHall}")
    public ResponseEntity<ApiResponse> updateTownHall(@PathVariable (value="idTownHall")long idTownHall, @RequestBody updateEmail update) {
        try {
            if(townHallRepository.findDistinctByIdTownHall(idTownHall).equals(null)){
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                        HttpStatus.BAD_REQUEST);
            }

            String email = update.getEmail();

            if(email.trim().equals("")){
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Email inválido."),
                        HttpStatus.BAD_REQUEST);
            }

            long idLogin = townHallRepository.findDistinctByIdTownHall(idTownHall).getLogin().getIdLogin();

            loginRepository.updateLoginEmail(email,idLogin);

            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Email updated.", idTownHall),
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

    @DeleteMapping("/townhalls/{idTownHall}")
    public ResponseEntity<ApiResponse> deleteTownHall(@PathVariable (value="idTownHall")long idTownHall) {

       TownHall townHall = townHallRepository.findDistinctByIdTownHall(idTownHall);
        Login login = loginRepository.findDistinctByIdLogin(townHall.getLogin().getIdLogin());
        townHallRepository.delete(townHall);
        loginRepository.delete(login);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "TownHall deleted.", idTownHall),
                HttpStatus.CREATED);

    }
}

