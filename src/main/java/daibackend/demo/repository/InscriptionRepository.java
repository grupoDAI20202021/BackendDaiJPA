package daibackend.demo.repository;

import daibackend.demo.model.Activity;
import daibackend.demo.model.Child;
import daibackend.demo.model.Inscription;
import daibackend.demo.model.InscriptionId;
import daibackend.demo.model.custom.InscriptionActivitiesByChildList;
import daibackend.demo.model.custom.InscriptionChildrenByActivityList;
import daibackend.demo.model.custom.RankList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface InscriptionRepository extends JpaRepository<Inscription, InscriptionId> {

    @Query(value ="Select new daibackend.demo.model.custom.RankList(C.idChild,C.name,C.age,Sum(I.evaluation),C.idAvatar) from child C left join inscription I on C.idChild=I.child.idChild where I.active=?1 group by C.idChild order by SUM(I.evaluation)  desc ")
    List <RankList> findRankByPoints(int active);


    Inscription findDistinctByActivityAndChild(Activity activity, Child child);

    @Query(value = "SELECT new daibackend.demo.model.custom.InscriptionActivitiesByChildList(A.idActivity,A.init_data,A.end_data, I.evaluation, A.title, AT.name,A.address) FROM activity_Type AT,inscription I, activity A, child C  where A.idActivity= I.activity.idActivity and C.idChild=?1  and C.idChild=I.child.idChild and I.presence=?2 and AT.idActivityType=A.activityType.idActivityType and I.active=?3" )
    List<InscriptionActivitiesByChildList>  findAllByActivityChild(long idChild,int presence,int active);


    @Query(value = "SELECT new daibackend.demo.model.custom.InscriptionActivitiesByChildList(A.idActivity,A.init_data,A.end_data, I.evaluation, A.title, AT.name,A.address) FROM activity_Type AT,inscription I, activity A, child C  where A.idActivity= I.activity.idActivity and C.idChild=?1 and C.idChild=I.child.idChild and A.status=?2 and AT.idActivityType=A.activityType.idActivityType and I.active=?3" )
    List<InscriptionActivitiesByChildList>  findAllByCurrentActivityChild(long idChild,String status,int active);



    @Query(value = "SELECT new daibackend.demo.model.custom.InscriptionChildrenByActivityList(C.idChild,C.name,C.age) FROM inscription I, child C  where I.activity.idActivity=?1 and C.idChild=I.child.idChild and I.active=?2" )
    List<InscriptionChildrenByActivityList>  findAllByChildActivity(long idActivity,int active);

    @Transactional
    @Modifying
    @Query(value = "update inscription I set I.presence=?1 WHERE I.child.idChild=?2 and I.activity.idActivity=?3")
    void updatePresence(int presence,long id_child, long id_activity);

    @Transactional
    @Modifying
    @Query(value = "update inscription I set I.evaluation=?1 WHERE I.child.idChild=?2 and I.activity.idActivity=?3")
    void updateEvaluation(int evaluation,long id_child, long id_activity);

    @Transactional
    @Modifying
    @Query(value = "update inscription I set I.active=?1 WHERE I.child.idChild=?2 and I.activity.idActivity=?3 and I.generatedCode=?4")
    void updateActive(int active,long id_child, long id_activity,int generatedCode);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM OAcmZAopsu.inscription WHERE OAcmZAopsu.inscription.id_activity=? and OAcmZAopsu.inscription.id_child=?;",nativeQuery = true )
    void deleteInscriptionByActivityAndChild(long idActivity, long idChild);

    @Transactional
    @Modifying
    @Query(value = "Insert into OAcmZAopsu.inscription (OAcmZAopsu.inscription.id_child,OAcmZAopsu.inscription.id_activity,OAcmZAopsu.inscription.presence,OAcmZAopsu.inscription.evaluation,OAcmZAopsu.inscription.active,OAcmZAopsu.inscription.generated_code) Values (?,?,?,?,?,?)",nativeQuery = true )
    void saveInscription(long idChild, long idActivity,int presence, int evaluation,int active, int generatedCode);
}