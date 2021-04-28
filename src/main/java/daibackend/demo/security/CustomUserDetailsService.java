package daibackend.demo.security;

import daibackend.demo.model.*;
import daibackend.demo.payload.response.ApiResponse;
import daibackend.demo.payload.response.JwtAuthenticationResponseRole;
import daibackend.demo.repository.*;
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

    @Autowired
    AdministratorRepository administratorRepository;

    @Autowired
    TownHallRepository townHallRepository;

    @Autowired
    InstitutionRepository institutionRepository;

    @Autowired
    ChildRepository childRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Let people login with or email
        try {
            Login user = loginRepository.findDistinctByEmailAndActive(email,1);

            if (user.getRole().getIdRole()==0){
                Administrator administrator = administratorRepository.findDistinctByLogin(user);
                return UserPrincipal.create(administrator.getIdAdministrator(),user.getEmail(), user.getPassword(), user.getRole().getName().name());
            }

            if (user.getRole().getIdRole()==1){
                TownHall townHall = townHallRepository.findDistinctByLogin(user);
                return UserPrincipal.create(townHall.getIdTownHall(),user.getEmail(), user.getPassword(), user.getRole().getName().name());
            }

            if (user.getRole().getIdRole()==2){
                Institution institution = institutionRepository.findDistinctByLogin(user);
                return  UserPrincipal.create(institution.getIdInstitution(),user.getEmail(), user.getPassword(), user.getRole().getName().name());
            } else {
                Child child = childRepository.findDistinctByLogin(user);
                return UserPrincipal.create(child.getIdChild(),user.getEmail(), user.getPassword(), user.getRole().getName().name());
            }

        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found with email : " + email);
        }
    }

    // This method is used by JWTAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(Long idLogin) {
        try {
            Login user = loginRepository.findDistinctByIdLogin(idLogin);
            if (user.getRole().getIdRole()==0){
                Administrator administrator = administratorRepository.findDistinctByLogin(user);
                return UserPrincipal.create(administrator.getIdAdministrator(),user.getEmail(), user.getPassword(), user.getRole().getName().name());
            }

            if (user.getRole().getIdRole()==1){
                TownHall townHall = townHallRepository.findDistinctByLogin(user);
                return UserPrincipal.create(townHall.getIdTownHall(),user.getEmail(), user.getPassword(), user.getRole().getName().name());
            }

            if (user.getRole().getIdRole()==2){
                Institution institution = institutionRepository.findDistinctByLogin(user);
                return  UserPrincipal.create(institution.getIdInstitution(),user.getEmail(), user.getPassword(), user.getRole().getName().name());
            } else {
                Child child = childRepository.findDistinctByLogin(user);
                return UserPrincipal.create(child.getIdChild(),user.getEmail(), user.getPassword(), user.getRole().getName().name());
            }
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found with id : " + idLogin);
        }
    }

    @Transactional
    public UserDetails loadUserByEmail(String email) {
        try {
            Login user = loginRepository.findDistinctByEmailAndActive(email,1);
            if (user.getRole().getIdRole()==0){
                Administrator administrator = administratorRepository.findDistinctByLogin(user);
                return UserPrincipal.create(administrator.getIdAdministrator(),user.getEmail(), user.getPassword(), user.getRole().getName().name());
            }

            if (user.getRole().getIdRole()==1){
                TownHall townHall = townHallRepository.findDistinctByLogin(user);
                return UserPrincipal.create(townHall.getIdTownHall(),user.getEmail(), user.getPassword(), user.getRole().getName().name());
            }

            if (user.getRole().getIdRole()==2){
                Institution institution = institutionRepository.findDistinctByLogin(user);
                return  UserPrincipal.create(institution.getIdInstitution(),user.getEmail(), user.getPassword(), user.getRole().getName().name());
            } else {
                Child child = childRepository.findDistinctByLogin(user);
                return UserPrincipal.create(child.getIdChild(),user.getEmail(), user.getPassword(), user.getRole().getName().name());
            }
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found with email : " + email);
        }
    }
}
