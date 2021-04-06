package daibackend.demo.repository;

import daibackend.demo.model.Child;
import daibackend.demo.model.Sugestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface SugestionRepository extends JpaRepository<Sugestion, Long> {
    Sugestion findDistinctByIdSugestion(long id_sugestion);

    List<Sugestion> findAllByChecked(int checked);


    List<Sugestion> findAllByChild(Child child);

    @Transactional
    @Modifying
    @Query("UPDATE sugestion SET checked = ?1 WHERE idSugestion = ?2")
    void updateSugestion(int checked, Long id_sugestion);

    @Override
    void delete(Sugestion sugestion);
}
