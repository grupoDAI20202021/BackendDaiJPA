package daibackend.demo.repository;

import daibackend.demo.model.Activity;
import daibackend.demo.model.Child;
import daibackend.demo.model.Inscription;
import daibackend.demo.model.InscriptionId;
import daibackend.demo.model.custom.InscriptionActivitiesByChildList;
import daibackend.demo.model.custom.InscriptionChildrenByActivityList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface InscriptionRepository extends JpaRepository<Inscription, InscriptionId> {

    Inscription findDistinctByActivityAndChild(Activity activity, Child child);

    @Query(value = "SELECT new daibackend.demo.model.custom.InscriptionActivitiesByChildList(A.idActivity,A.end_data, I.evaluation, A.title, AT.name) FROM activity_Type AT,inscription I, activity A, child C  where A.idActivity= I.activity.idActivity and C.idChild=?1 and I.presence=?2 and AT.idActivityType=A.activityType.idActivityType" )
    List<InscriptionActivitiesByChildList>  findAllByActivityChild(long idChild,int presence);

    @Query(value = "SELECT new daibackend.demo.model.custom.InscriptionChildrenByActivityList(C.idChild,C.name,C.age) FROM inscription I, child C  where I.activity.idActivity=?1 " )
    List<InscriptionChildrenByActivityList>  findAllByChildActivity(long idActivity);

    @Transactional
    @Modifying
    @Query(value = "update inscription I set I.presence=?1 WHERE I.child.idChild=?2 and I.activity.idActivity=?3")
    void updatePresence(int presence,long id_child, long id_activity);

    @Transactional
    @Modifying
    @Query(value = "update inscription I set I.evaluation=?1 WHERE I.child.idChild=?2 and I.activity.idActivity=?3")
    void updateEvaluation(int evaluation,long id_child, long id_activity);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM OAcmZAopsu.inscription WHERE OAcmZAopsu.inscription.id_activity=? and OAcmZAopsu.inscription.id_child=?;",nativeQuery = true )
    void deleteInscriptionByActivityAndChild(long idActivity, long idChild);

    @Transactional
    @Modifying
    @Query(value = "Insert into OAcmZAopsu.inscription (OAcmZAopsu.inscription.id_child,OAcmZAopsu.inscription.id_activity,OAcmZAopsu.inscription.presence,OAcmZAopsu.inscription.evaluation) Values (?,?,?,?)",nativeQuery = true )
    void saveInscription(long idChild, long idActivity,int presence, int evaluation);
}