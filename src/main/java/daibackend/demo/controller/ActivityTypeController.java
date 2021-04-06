package daibackend.demo.controller;

import daibackend.demo.model.Activity;
import daibackend.demo.model.custom.DataActivityType;
import daibackend.demo.repository.ActivityTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class ActivityTypeController {
    @Autowired
    ActivityTypeRepository activityTypeRepository;

    //@PreAuthorize("hasRole('GUARD') or hasRole('MANAGER') or hasRole('NETWORKMAN')")
    @GetMapping("/activitiestype/{idActivityType}")
    public DataActivityType findActivityTypeData(/*@CurrentUser UserPrincipal currentUser*/@PathVariable long idActivityType) {
        int quantity= activityTypeRepository.getQuantity(idActivityType);
        int presences = activityTypeRepository.getPresence(idActivityType,1);
        int inscriptions = activityTypeRepository.getInscription(idActivityType);
        float feedback = activityTypeRepository.getFeedback(idActivityType);

        DataActivityType dataActivityType = new DataActivityType(quantity,inscriptions,presences,feedback);
        //User userLogged = userRepository.findByUserId(currentUser.getId());
        //Set<Role> roleUserLogged = userLogged.getRoles();

        // Get Permissions
        /*if (String.valueOf(roleUserLogged).equals("[Role [id=0]]")
                || String.valueOf(roleUserLogged).equals("[Role [id=1]]")) {
            return alertLogRepository.findAlertLogsByPrison(userLogged.getPrison());
        }*/
        return dataActivityType;
    }
}
