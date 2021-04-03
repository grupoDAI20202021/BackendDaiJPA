package daibackend.demo.security;

import daibackend.demo.model.Login;
import daibackend.demo.payload.response.ApiResponse;
import daibackend.demo.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    LoginRepository loginRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Let people login with or email
        try {
            Login user = loginRepository.findDistinctByEmail(email);
            return UserPrincipal.create(user);
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found with email : " + email);
        }
    }

    // This method is used by JWTAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(Long idLogin) {
        try {
            Login user = loginRepository.findDistinctByIdLogin(idLogin);
            return UserPrincipal.create(user);
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found with id : " + idLogin);
        }
    }
}
