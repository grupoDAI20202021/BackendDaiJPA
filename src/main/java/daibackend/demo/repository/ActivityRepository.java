package daibackend.demo.repository;


import daibackend.demo.model.Activity;
import daibackend.demo.model.Institution;

import daibackend.demo.model.Sponsor;
import daibackend.demo.model.TownHall;
import daibackend.demo.model.custom.ActivitiesList;
import daibackend.demo.model.custom.ActivityList;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    Activity findByIdActivity(long id_activity);

    @Query("SELECT Count(A) FROM activity A WHERE A.init_data between ?1 and ?2 ORDER BY A.idActivity DESC")
    int findAllByInit_dataBetween( Date init_month, Date end_month);

    List<Activity> findAll();

    @Query(value="Select new daibackend.demo.model.custom.ActivityList(A.title,A.init_data,A.end_data,A.status,A.activityType.name,A.idActivity) From activity A, activity_Type AT where A.activityType.idActivityType=AT.idActivityType order by A.idActivity  asc ")
    List<ActivityList> findLast8(PageRequest pageable);


     @Query("SELECT  new daibackend.demo.model.custom.ActivitiesList(A.title,A.address, A.init_data,A.end_data,S.name,A.activityType.name,A.idActivity) FROM activity A,sponsor S WHERE A.status = ?1 and A.institution.idInstitution=?2 and A.sponsor.idSponsor=S.idSponsor ORDER BY A.idActivity DESC")
    List<ActivitiesList> findActivitiesByStatus(String status, long idInstitution);

    @Query("SELECT A FROM activity A,townHall T WHERE A.status = ?1 and T.idTownHall=?2 and A.institution.townHall.idTownHall= T.idTownHall ORDER BY A.idActivity DESC")
    List<Activity> findActivitiesByStatusAndTownHall(String status, long townhall);

    @Query("SELECT A FROM activity A,townHall T WHERE T.idTownHall=?1 and A.institution.townHall.idTownHall= T.idTownHall ORDER BY A.idActivity DESC")
    List<Activity> findActivitiesTownHall( long townhall);

    @Transactional
    @Modifying
    @Query("UPDATE activity A SET A.status= ?1 where A.idActivity = ?2")
    void updateActivityStatus(String status ,Long idActivity);

    @Transactional
    @Modifying
    @Query("UPDATE activity A SET A.evaluation= ?1, A.status=?2 where A.idActivity = ?3")
    void updateActivityEvaluation(int points , String status, Long idActivity);

    @Transactional
    @Modifying
    @Query("UPDATE activity A SET A.address= ?1, A.init_data= ?2 ,A.end_data= ?3, A.spaces=?4 WHERE A.idActivity = ?5")
    void updateActivityAsInstituition(String address, Date init_data, Date end_data,int spaces, Long idActivity);  // ver se é import Data sql ou java


    @Transactional
    @Modifying
    @Query("UPDATE activity A SET A.sponsor.idSponsor= ?1, A.init_data= ?2 ,A.end_data= ?3, A.status=?5 WHERE A.idActivity = ?4")
    void updateActivityAsTownHall(long idSponsor, Date init_data, Date end_data,Long idActivity,String status);

    @Override
    void delete(Activity activity);


    @Query("SELECT  Count(A) FROM activity A,activity_Type AT WHERE A.activityType.idActivityType=AT.idActivityType  and A.status = ?1 and A.activityType.idActivityType=?2  ORDER BY A.idActivity DESC")
    int findActivitiesByStatusNumber(String status,long idActivityType);
}
