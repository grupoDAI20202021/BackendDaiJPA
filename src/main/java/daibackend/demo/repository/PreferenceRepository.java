package daibackend.demo.repository;

import daibackend.demo.model.ActivityType;
import daibackend.demo.model.Child;
import daibackend.demo.model.Preference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {
    Preference findDistinctByIdPreference(long id_preference);

    List<Preference> findAllByChild(Child child);

    void deleteDistinctByActivityTypeAndChild(ActivityType activityType,Child child);


}
