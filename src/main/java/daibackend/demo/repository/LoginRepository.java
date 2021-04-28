package daibackend.demo.repository;

import daibackend.demo.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, Long> {
    Login findDistinctByIdLogin(long id_login);

    @Override
    Optional<Login> findById(Long aLong);

    Login findDistinctByEmailAndActive(String email,int active);

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

    @Transactional
    @Modifying
    @Query("UPDATE login L SET L.active = ?1 WHERE L.email = ?2 and L.generatedCode=?3")
    void updateLoginChildActive(int active, String email, int generatedCode);

    @Transactional
    @Modifying
    @Query("UPDATE login L SET L.active = ?1 WHERE L.idLogin = ?2")
    void updateLoginDeactivate(int active,  long idLogin);

}
