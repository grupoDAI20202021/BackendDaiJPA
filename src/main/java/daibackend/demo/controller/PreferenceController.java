package daibackend.demo.controller;

import daibackend.demo.model.ActivityType;
import daibackend.demo.model.Child;
import daibackend.demo.model.Preference;
import daibackend.demo.model.custom.DataPreferenceDashboard;
import daibackend.demo.model.custom.PreferenceActivitiesByChild;
import daibackend.demo.payload.response.ApiResponse;
import daibackend.demo.repository.ActivityTypeRepository;
import daibackend.demo.repository.ChildRepository;
import daibackend.demo.repository.PreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class PreferenceController {
    @Autowired
    ChildRepository childRepository;

    @Autowired
    PreferenceRepository preferenceRepository;

    @Autowired
    ActivityTypeRepository activityTypeRepository;

    //@PreAuthorize("hasRole('ADMINISTRATOR') or hasROLE('CHILD')")
    @GetMapping("/preferences/{idChild}")
    public List<Preference> listActivitiesTypeByChild(/*@CurrentUser UserPrincipal currentUser*/ @PathVariable long idChild) {

        Child child = childRepository.findDistinctByIdChild(idChild);
        return preferenceRepository.findAllByChild(child);
    }

    //@PreAuthorize("hasRole('ADMINISTRATOR')  or hasROLE('CHILD')")
    @GetMapping("/preferences/{idChild}/activities")
    public List<PreferenceActivitiesByChild> listPreferenceActivitiesByChild(/*@CurrentUser UserPrincipal currentUser*/ @PathVariable long idChild) {
        Child child = childRepository.findDistinctByIdChild(idChild);
        String status = "Aprovada";
        return preferenceRepository.findAllPreferenceActivitiesByChild(idChild,status);
    }

    //@PreAuthorize("hasRole('GUARD') or hasRole('MANAGER') or hasRole('NETWORKMAN')") // função para contar numero preferencias por atividade
    /*@GetMapping("/preferences/{idActivityType}/children")
    public List<PreferenceActivitiesByChild> listPreferenceActivitiesByChild(/*@CurrentUser UserPrincipal currentUser*/ //@PathVariable long idChild)

   // {
        //User userLogged = userRepository.findByUserId(currentUser.getId());
        //Set<Role> roleUserLogged = userLogged.getRoles();

        // Get Permissions
        /*if (String.valueOf(roleUserLogged).equals("[Role [id=0]]")
                || String.valueOf(roleUserLogged).equals("[Role [id=1]]")) {
            return alertLogRepository.findAlertLogsByPrison(userLogged.getPrison());
        }*/
        /*Child child = childRepository.findDistinctByIdChild(idChild);
        String status = "Aprovada";
        return preferenceRepository.findAllPreferenceActivitiesByChild(idChild,status);
    }*/
   // }

   @PostMapping("/preferences/{idChild}/{idActivityType}") // Creat preference
    public ResponseEntity<ApiResponse> savePreference(@PathVariable long idChild, @PathVariable long idActivityType) {
        try {
            Child child = childRepository.findDistinctByIdChild(idChild);
            ActivityType activityType = activityTypeRepository.findDistinctByIdActivityType(idActivityType);

            if (childRepository.findDistinctByIdChild(idChild).equals(null)) {
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Criança não existe."),
                        HttpStatus.BAD_REQUEST);
            }

            if (activityTypeRepository.findDistinctByIdActivityType(idActivityType).equals(null)) {
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Activity type inválida."),
                        HttpStatus.BAD_REQUEST);
            }
            Preference P = new Preference(null,child,activityType);
            preferenceRepository.save(P);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Preference created",P.getChild().getIdChild()),
                    HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                    HttpStatus.BAD_REQUEST);
        }
    }

   // @PreAuthorize("hasRole('ADMINISTRATOR') or hasROLE('CHILD')")
    @DeleteMapping("/preferences/{idChild}/{idActivityType}")
    public ResponseEntity<ApiResponse> deletePreference(@PathVariable (value="idChild")long idChild, @PathVariable (value="idActivityType")long idActivityType) {
        Child child = childRepository.findDistinctByIdChild(idChild);
        ActivityType activityType = activityTypeRepository.findDistinctByIdActivityType(idActivityType);
        Preference preference = preferenceRepository.findDistinctByChildAndActivityType(child,activityType);
        preferenceRepository.delete(preference);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Preference deleted.", preference.getIdPreference()),
                HttpStatus.CREATED);

    }

    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TOWNHALL') or hasRole('INSTITUTION')")
    @GetMapping("/preferences/dashboard")
    public List<DataPreferenceDashboard> listPreferenceDashboard(/*@CurrentUser UserPrincipal currentUser*/ ) {
        return preferenceRepository.findQuantity(PageRequest.of(0,3));
    }
}
