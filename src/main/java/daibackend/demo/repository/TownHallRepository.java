package daibackend.demo.repository;


import daibackend.demo.model.Image;
import daibackend.demo.model.Institution;
import daibackend.demo.model.Login;
import daibackend.demo.model.TownHall;
import daibackend.demo.model.custom.TownHallList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface TownHallRepository extends JpaRepository<TownHall, Long> {

    TownHall findDistinctByIdTownHall(long id_townhall);

    TownHall findDistinctByLogin(Login login);

    @Query(value = "SELECT new daibackend.demo.model.custom.TownHallList(L.email,T.name,T.address,T.idTownHall) FROM login L, townHall T where L.idLogin= T.login.idLogin and T.active=?1" )
    List<TownHallList> findAllTownHallList(int active);


    @Transactional
    @Modifying
    @Query("UPDATE townHall H SET H.active=?1 where H.idTownHall = ?2")
    void deleteLogic(int active , Long idTownHall);

    @Override
    void delete(TownHall townHall);
}
