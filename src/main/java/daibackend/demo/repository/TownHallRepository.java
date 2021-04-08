package daibackend.demo.repository;


import daibackend.demo.model.TownHall;
import daibackend.demo.model.custom.TownHallList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TownHallRepository extends JpaRepository<TownHall, Long> {

    TownHall findDistinctByIdTownHall(long id_townhall);

    @Query(value = "SELECT new daibackend.demo.model.custom.TownHallList(L.email,T.name,T.address,T.idTownHall) FROM login L, townHall T where L.idLogin= T.login.idLogin" )
    List<TownHallList> findAllTownHallList();

    @Override
    void delete(TownHall townHall);
}
