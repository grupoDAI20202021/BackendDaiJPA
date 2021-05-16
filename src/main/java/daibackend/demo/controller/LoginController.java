package daibackend.demo.controller;

import daibackend.demo.model.custom.updateLoginActive;
import daibackend.demo.payload.response.ApiResponse;
import daibackend.demo.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api")
public class LoginController {

    @Autowired
    LoginRepository loginRepository;



    @GetMapping("/login/activate")
    public ResponseEntity<ApiResponse> updateChildActivate(@RequestParam String updateEmail,@RequestParam int  updateGeneratedCode) {
        try {
            String email = updateEmail;
            int generatedCode = updateGeneratedCode;


            if(email.trim().equals("")){
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Email inválido."),
                        HttpStatus.BAD_REQUEST);
            }

            loginRepository.updateLoginChildActive(1,email,generatedCode);

            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Email updated.", loginRepository.findDistinctByEmailAndActive(email,1).getIdLogin()),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
