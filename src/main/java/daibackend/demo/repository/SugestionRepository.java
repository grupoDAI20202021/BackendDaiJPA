package daibackend.demo.repository;

import daibackend.demo.model.Child;
import daibackend.demo.model.Sugestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SugestionRepository extends JpaRepository<Sugestion, Long> {
    Sugestion findDistinctByIdSugestion(long id_sugestion);

    List<Sugestion> findAllByChecked(int checked);


    List<Sugestion> findAllByChild(Child child);

    @Override
    void delete(Sugestion sugestion);
}
