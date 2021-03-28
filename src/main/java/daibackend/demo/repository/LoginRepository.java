package daibackend.demo.repository;

import daibackend.demo.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login, Long> {
    Login findDistinctByIdLogin(long id_login);

    
}
