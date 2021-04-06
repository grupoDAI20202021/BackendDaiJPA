package daibackend.demo.repository;

import daibackend.demo.model.ActivityType;
import daibackend.demo.model.Child;
import daibackend.demo.model.Preference;
import daibackend.demo.model.custom.PreferenceActivitiesByChild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {
    Preference findDistinctByIdPreference(long id_preference);

    Preference findDistinctByChildAndActivityType(Child child,ActivityType activityType);

    List<Preference> findAllByChild(Child child);


    @Query("SELECT new daibackend.demo.model.custom.PreferenceActivitiesByChild(A.idActivity,AT.name,A.title,A.init_data,A.end_data,A.address) FROM activity_Type AT, activity A, child C,preference P where A.activityType.idActivityType= AT.idActivityType and  P.activityType.idActivityType=A.activityType.idActivityType and C.idChild=?1 and A.status=?2 and AT.idActivityType=A.activityType.idActivityType")
    List<PreferenceActivitiesByChild> findAllPreferenceActivitiesByChild(long idChild,String status);

    void deleteDistinctByActivityTypeAndChild(ActivityType activityType,Child child);


}
