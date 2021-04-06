package daibackend.demo.repository;

import daibackend.demo.model.Activity;
import daibackend.demo.model.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ActivityTypeRepository extends JpaRepository<ActivityType, Long> {

    ActivityType findDistinctByIdActivityType(long idActivityType);
}
