package daibackend.demo.controller;

import daibackend.demo.model.custom.updateLoginActive;
import daibackend.demo.payload.response.ApiResponse;
import daibackend.demo.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api")
public class LoginController {

    @Autowired
    LoginRepository loginRepository;


    //@PreAuthorize("hasRole('GUARD') or hasRole('MANAGER') or hasRole('NETWORKMAN')")  // Child
    @PutMapping("/login/activate")
    public ResponseEntity<ApiResponse> updateChildActivate(@RequestBody updateLoginActive update) {
        try {
            String email = update.getEmail();
            int generatedCode = update.getGeneratedCode();


            if(email.trim().equals("")){
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Email inválido."),
                        HttpStatus.BAD_REQUEST);
            }

            loginRepository.updateLoginChildActive(1,email,generatedCode);

            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Email updated.", loginRepository.findDistinctByEmailAndActive(email,1).getIdLogin()),
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
}
