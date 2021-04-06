package daibackend.demo.controller;

import daibackend.demo.model.ActivityType;
import daibackend.demo.model.Child;
import daibackend.demo.model.Preference;
import daibackend.demo.model.Sugestion;
import daibackend.demo.payload.response.ApiResponse;
import daibackend.demo.repository.ChildRepository;
import daibackend.demo.repository.SugestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class SugestionController {

    @Autowired
    SugestionRepository sugestionRepository;

    @Autowired
    ChildRepository childRepository;

    //@PreAuthorize("hasRole('GUARD') or hasRole('MANAGER') or hasRole('NETWORKMAN')")
    @GetMapping("/sugestions")
    public List<Sugestion> listSugestions(/*@CurrentUser UserPrincipal currentUser*/) {
        //User userLogged = userRepository.findByUserId(currentUser.getId());
        //Set<Role> roleUserLogged = userLogged.getRoles();

        // Get Permissions
        /*if (String.valueOf(roleUserLogged).equals("[Role [id=0]]")
                || String.valueOf(roleUserLogged).equals("[Role [id=1]]")) {
            return alertLogRepository.findAlertLogsByPrison(userLogged.getPrison());
        }*/
        return sugestionRepository.findAllByChecked(0);
    }

    //@PreAuthorize("hasRole('GUARD') or hasRole('MANAGER') or hasRole('NETWORKMAN')")
    @GetMapping("/sugestions/{idSugestion}")
    public Sugestion listSugestionsById(/*@CurrentUser UserPrincipal currentUser*/@PathVariable long idSugestion) {
        //User userLogged = userRepository.findByUserId(currentUser.getId());
        //Set<Role> roleUserLogged = userLogged.getRoles();

        // Get Permissions
        /*if (String.valueOf(roleUserLogged).equals("[Role [id=0]]")
                || String.valueOf(roleUserLogged).equals("[Role [id=1]]")) {
            return alertLogRepository.findAlertLogsByPrison(userLogged.getPrison());
        }*/
        return sugestionRepository.findDistinctByIdSugestion(idSugestion);
    }

    @GetMapping("/sugestions/children/{idChild}")
    public List<Sugestion> listSugestionsByChild(/*@CurrentUser UserPrincipal currentUser*/@PathVariable long idChild) {
        Child child = childRepository.findDistinctByIdChild(idChild);
        //User userLogged = userRepository.fndByUserId(currentUser.getId());
        //Set<Role> roleUserLogged = userLogged.getRoles();

        // Get Permissions
        /*if (String.valueOf(roleUserLogged).equals("[Role [id=0]]")
                || String.valueOf(roleUserLogged).equals("[Role [id=1]]")) {
            return alertLogRepository.findAlertLogsByPrison(userLogged.getPrison());
        }*/
        return sugestionRepository.findAllByChild(child);
    }

    @PostMapping("/sugestions") // Creat sugestion
    public ResponseEntity<ApiResponse> saveSugestion() {
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
}
