package daibackend.demo.repository;

import daibackend.demo.model.Activity;
import daibackend.demo.model.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ActivityTypeRepository extends JpaRepository<ActivityType, Long> {

    ActivityType findDistinctByIdActivityType(long idActivityType);

    @Query(value="Select coalesce(Count(A),0) From activity A,activity_Type AT where A.activityType.idActivityType=?1 and A.activityType.idActivityType=AT.idActivityType")
    int getQuantity(long idActivityType);

    @Query(value="Select coalesce(Count(I),0) From activity A,activity_Type AT,inscription I where A.activityType.idActivityType=?1 and A.activityType.idActivityType=AT.idActivityType and I.activity.idActivity=A.idActivity")
    int getInscription(long idActivityType);

    @Query(value="Select coalesce( Count(I),0) From activity A,activity_Type AT,inscription I where A.activityType.idActivityType=?1 and I.presence=?2 and A.activityType.idActivityType=AT.idActivityType and I.activity.idActivity=A.idActivity")
    int getPresence(long idActivityType,int presence );

    @Query(value="Select coalesce(AVG(I.evaluation),0) From activity A,activity_Type AT,inscription I where A.activityType.idActivityType=?1 and A.activityType.idActivityType=AT.idActivityType and I.activity.idActivity=A.idActivity and I.evaluation is not null")
    float getFeedback(long idActivityType);
}
