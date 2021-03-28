package daibackend.demo.repository;

import daibackend.demo.model.Activity;
import daibackend.demo.model.Child;
import daibackend.demo.model.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface InscriptionRepository extends JpaRepository<Inscription, Long> {

    Inscription findDistinctByActivityAndChild(Activity activity, Child child);

    @Transactional
    @Modifying
    @Query(value = "update inscription I set I.presence=?1 WHERE I.child.idChild=?2 and I.activity.idActivity=?3")
    void updatePresence(int presence,long id_child, long id_activity);

    @Transactional
    @Modifying
    @Query(value = "update inscription I set I.evaluation=?1 WHERE I.child.idChild=?2 and I.activity.idActivity=?3")
    void updateEvaluation(int evaluation,long id_child, long id_activity);

    @Override
    void delete(Inscription inscription);
}
