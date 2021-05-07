package daibackend.demo.repository;

import daibackend.demo.model.Image;
import daibackend.demo.model.Sponsor;
import daibackend.demo.model.TownHall;
import daibackend.demo.model.custom.SponsorList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface SponsorRepository extends JpaRepository<Sponsor, Long> {
    Sponsor findDistinctByIdSponsor(long id_sponsor);

    @Override
    List<Sponsor> findAll();

    @Query("SELECT new daibackend.demo.model.custom.SponsorList(S.idSponsor,S.name,S.insert_data,S.image.idImage,S.image,S.active) FROM sponsor S where S.townHall.idTownHall=?1")
    List<SponsorList> findAllByTownHall(long idTownHall);

    @Transactional
    @Modifying
    @Query("UPDATE sponsor S SET S.name= ?1 where S.idSponsor = ?2")
    void updateSponsorName(String name ,Long id_sponsor);

    @Transactional
    @Modifying
    @Query("UPDATE sponsor S SET S.image= ?1 where S.idSponsor = ?2")
    void updateSponsorImage(Image image , Long id_sponsor);

    @Transactional
    @Modifying
    @Query("UPDATE sponsor S SET S.image.idImage = ?1 WHERE S.idSponsor = ?2")
    void updateSponsorPhotoId(Long idImage, Long idSponsor);

    @Transactional
    @Modifying
    @Query("UPDATE sponsor S SET S.active=?1 where S.idSponsor = ?2")
    void deleteLogic(int active , Long idSponsor);

    @Override
    void delete(Sponsor sponsor);

    boolean existsByName(String name);
}
