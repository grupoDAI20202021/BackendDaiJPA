package daibackend.demo.repository;

import daibackend.demo.model.Institution;
import daibackend.demo.model.Login;
import daibackend.demo.model.custom.InstitutionList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface InstitutionRepository extends JpaRepository<Institution, Long> {
    Institution findDistinctByIdInstitution(long id_institution);

    Institution findDistinctByLogin(Login login);

    @Query(value = "SELECT new daibackend.demo.model.custom.InstitutionList(L.email,T.name,T.address,T.townHall.name,T.idInstitution) FROM login L, institution T where L.idLogin= T.login.idLogin  and T.active=?1" )
    List<InstitutionList> findAllInstitution(int active);

    @Transactional
    @Modifying
    @Query("UPDATE institution I SET I.name= ?1, I.address= ?2 where I.idInstitution=?3")
    void updateInstitution(String name ,String address, long id_institution);

    @Override
    void delete(Institution institution);

    @Query(value="SELECT T.townHall.idTownHall FROM  institution T where T.idInstitution=?1")
    long getTownhallId(long idInstitution);
}
