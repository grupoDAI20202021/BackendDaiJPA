package daibackend.demo.repository;

import daibackend.demo.model.Image;
import daibackend.demo.model.Sponsor;
import daibackend.demo.model.TownHall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface SponsorRepository extends JpaRepository<Sponsor, Long> {
    Sponsor findDistinctByIdSponsor(long id_sponsor);

    @Override
    List<Sponsor> findAll();

    List<Sponsor> findAllByTownHall(TownHall townHall);

    @Transactional
    @Modifying
    @Query("UPDATE sponsor S SET S.name= ?1 where S.idSponsor = ?2")
    void updateSponsorName(String name ,Long id_sponsor);

    @Transactional
    @Modifying
    @Query("UPDATE sponsor S SET S.image= ?1 where S.idSponsor = ?2")
    void updateSponsorImage(Image image , Long id_sponsor);

    @Override
    void delete(Sponsor sponsor);
}
