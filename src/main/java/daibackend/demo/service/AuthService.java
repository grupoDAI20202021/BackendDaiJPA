package daibackend.demo.service;

//import daibackend.demo.exception.ResourceNotFoundException;
//import daibackend.demo.model.Login;
//import daibackend.demo.model.custom.LoginRequest;
//import daibackend.demo.payload.response.ApiResponse;
//import daibackend.demo.payload.response.JwtAuthenticationResponseRole;
//import daibackend.demo.repository.LoginRepository;
//import daibackend.demo.repository.RoleRepository;
//import daibackend.demo.security.JwtTokenProvider;
//import daibackend.demo.util.CookieUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.validation.Valid;
//import java.text.ParseException;
//
//@Service
//public class AuthService {
//
//    @Autowired
//    AuthenticationManager authenticationManager;
//
//    @Autowired
//    LoginRepository loginRepository;
//
//    @Autowired
//    RoleRepository roleRepository;
//
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//    @Autowired
//    JwtTokenProvider tokenProvider;
//
//    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest,
//                                              HttpServletResponse response) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
//    try {
//    Login user = loginRepository.findDistinctByEmail(loginRequest.getEmail());
//        String roleString = "";
//        roleString = user.getRole().getName().toString();
//        if (user.getRole().getIdRole().equals(0)){
//
//        }
//
//        if (user.getRole().getIdRole().equals(1)){
//
//        }
//
//        if (user.getRole().getIdRole().equals(2)){
//
//        } else {
//
//        }
//    }
//    catch (ResourceNotFoundException e) {
//    return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Login not found"),
//            HttpStatus.INTERNAL_SERVER_ERROR);
//    }
////        Date today = new Date();
////        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////        String strDate = dateFormat.format(today);
//        //            Date date = dateFormat.parse(strDate);
////            user.setLastLogin(date);
////        String roleString = "";
////        Set<Role> roles = user.getRoles();
////        for (Role role : roles) {
////            roleString = role.getName().toString();
////        }
//        userRepository.save(user);
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        String jwt = tokenProvider.generateToken(authentication);
//        CookieUtils.addCookie(response, "token", jwt, 604800000);
//
//        return ResponseEntity.ok(new JwtAuthenticationResponseRole(jwt, roleString, user.getUserId()));
//    }
//
//    public ResponseEntity<ApiResponse> logoutUser(HttpServletRequest request, HttpServletResponse response) {
//        boolean isOK = CookieUtils.deleteCookie(request, response, "token");
//
//        if (isOK == true) {
//            return ResponseEntity.ok().body(new ApiResponse(true, "User logged out successfully"));
//        }
//
//        return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Must be logged in to logout"),
//                HttpStatus.PRECONDITION_FAILED);
//    }
//}
