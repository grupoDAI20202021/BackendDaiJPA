package daibackend.demo.controller;

import daibackend.demo.model.ActivityType;
import daibackend.demo.model.custom.DataActivityTypeDashboard;
import daibackend.demo.repository.ActivityTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
public class ActivityTypeController {
    @Autowired
    ActivityTypeRepository activityTypeRepository;

    //@PreAuthorize("hasRole('GUARD') or hasRole('MANAGER') or hasRole('NETWORKMAN')")
    @GetMapping("/activitiestype/{idActivityType}")
    public DataActivityTypeDashboard findActivityTypeData(/*@CurrentUser UserPrincipal currentUser*/@PathVariable long idActivityType) {
        int quantity= activityTypeRepository.getQuantity(idActivityType);
        int presences = activityTypeRepository.getPresence(idActivityType,1);
        int inscriptions = activityTypeRepository.getInscription(idActivityType);
        float feedback = activityTypeRepository.getFeedback(idActivityType);
        if(quantity%1!=0){
            quantity=0;
        }
        if(presences%1!=0){
            presences=0;
        }
        if(inscriptions%1!=0){
            inscriptions=0;
        }
        if(feedback!= (float)feedback){
            feedback= (float)0;
        }

        DataActivityTypeDashboard dataActivityTypeDashboard = new DataActivityTypeDashboard(quantity,inscriptions,presences,feedback);
        //User userLogged = userRepository.findByUserId(currentUser.getId());
        //Set<Role> roleUserLogged = userLogged.getRoles();

        // Get Permissions
        /*if (String.valueOf(roleUserLogged).equals("[Role [id=0]]")
                || String.valueOf(roleUserLogged).equals("[Role [id=1]]")) {
            return alertLogRepository.findAlertLogsByPrison(userLogged.getPrison());
        }*/
        return dataActivityTypeDashboard;
    }

    //@PreAuthorize("hasRole('GUARD') or hasRole('MANAGER') or hasRole('NETWORKMAN')")
    @GetMapping("/activitiestype")
    public List<ActivityType> findActivityType(/*@CurrentUser UserPrincipal currentUser*/) {


        //User userLogged = userRepository.findByUserId(currentUser.getId());
        //Set<Role> roleUserLogged = userLogged.getRoles();

        // Get Permissions
        /*if (String.valueOf(roleUserLogged).equals("[Role [id=0]]")
                || String.valueOf(roleUserLogged).equals("[Role [id=1]]")) {
            return alertLogRepository.findAlertLogsByPrison(userLogged.getPrison());
        }*/
        return activityTypeRepository.findAll();
    }
}
