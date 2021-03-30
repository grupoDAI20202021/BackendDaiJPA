package daibackend.demo.repository;

import daibackend.demo.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface LoginRepository extends JpaRepository<Login, Long> {
    Login findDistinctByIdLogin(long id_login);

    Login findDistinctByEmail(String email);

    @Override
    List<Login> findAll();

    Boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE login SET email = ?1 WHERE idLogin = ?2")
    void updateLoginEmail(String email, Long id_login);

    @Transactional
    @Modifying
    @Query("UPDATE login SET password = ?1 WHERE idLogin = ?2")
    void updateLoginPassword(String password, Long id_login);
}
