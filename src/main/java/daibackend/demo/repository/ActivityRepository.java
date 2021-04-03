package daibackend.demo.repository;


import daibackend.demo.model.Activity;
import daibackend.demo.model.Institution;

import daibackend.demo.model.Sponsor;
import daibackend.demo.model.TownHall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    Activity findByIdActivity(long id_activity);

    List<Activity> findAll();

    List<Activity> findByStatusAndInstitution(String status, Institution institution);

     @Query("SELECT A FROM activity A WHERE A.status = ?1 and A.institution.idInstitution=?2 ORDER BY A.idActivity DESC")
    List<Activity> findActivitiesByStatus(String status, Institution institution);

    @Query("SELECT A FROM activity A,townHall T WHERE A.status = ?1 and T.idTownHall=?2 and A.institution.townHall.idTownHall= T.idTownHall ORDER BY A.idActivity DESC")
    List<Activity> findActivitiesByStatusAndTownHall(String status, TownHall townHall);

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
}
