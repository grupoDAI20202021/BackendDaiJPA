package daibackend.demo.repository;

import daibackend.demo.model.ActivityType;
import daibackend.demo.model.Administrator;
import daibackend.demo.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long> {

    Administrator findDistinctByLogin(Login login);
}