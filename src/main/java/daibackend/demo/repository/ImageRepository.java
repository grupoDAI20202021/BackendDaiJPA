package daibackend.demo.repository;

import daibackend.demo.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByName(String name);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM image WHERE id_image=(SELECT sponsor.id_image FROM sponsor WHERE id_sponsor = ?1)", nativeQuery = true)
    void deleteSponsorPhoto(Long id_sponsor);

}
