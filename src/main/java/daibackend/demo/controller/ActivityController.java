package daibackend.demo.controller;

import daibackend.demo.model.Activity;
import daibackend.demo.model.Institution;
import daibackend.demo.model.TownHall;
import daibackend.demo.model.custom.TownHallList;
import daibackend.demo.payload.response.ApiResponse;
import daibackend.demo.repository.ActivityRepository;
import daibackend.demo.repository.TownHallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;


@RestController
    @RequestMapping(value = "/api")
    public class ActivityController {
        @Autowired
        ActivityRepository activityRepository;

        @Autowired
        TownHallRepository townHallRepository;


        //@PreAuthorize("hasRole('GUARD') or hasRole('MANAGER') or hasRole('NETWORKMAN')")
        @GetMapping("/activities")
        public List<Activity> listActivities(/*@CurrentUser UserPrincipal currentUser*/) {
            //User userLogged = userRepository.findByUserId(currentUser.getId());
            //Set<Role> roleUserLogged = userLogged.getRoles();

            // Get Permissions
        /*if (String.valueOf(roleUserLogged).equals("[Role [id=0]]")
                || String.valueOf(roleUserLogged).equals("[Role [id=1]]")) {
            return alertLogRepository.findAlertLogsByPrison(userLogged.getPrison());
        }*/
            return activityRepository.findAll();
        }


        //@PreAuthorize("hasRole('GUARD') or hasRole('MANAGER') or hasRole('NETWORKMAN')")
        @GetMapping("/activities/by-townhall/{idTownHall}/{status}")
        public List<Activity> listActivitiesTownHall(@PathVariable(value = "idTownHall") long idTownHall, @PathVariable String status) {
        TownHall t = townHallRepository.findDistinctByIdTownHall(idTownHall);

            //User userLogged = userRepository.findByUserId(currentUser.getId());
            //Set<Role> roleUserLogged = userLogged.getRoles();

            // Get Permissions
        /*if (String.valueOf(roleUserLogged).equals("[Role [id=0]]")
                || String.valueOf(roleUserLogged).equals("[Role [id=1]]")) {
            return alertLogRepository.findAlertLogsByPrison(userLogged.getPrison());
        }*/
            return activityRepository.findActivitiesByStatusAndTownHall(status,t);
        }


        @PostMapping("/activities")
        public ResponseEntity<ApiResponse> saveActivity(@RequestBody Activity activity) {
            try {
                // Activity Attributes
                Long idActivity = activity.getIdActivity();
                Date init_data  = activity.getInit_data();
                Date end_data  = activity.getEnd_data();
                String status = "Por aprovar";
                int spaces = activity.getSpaces();
                Institution institution = activity.getInstitution();
                if (init_data.compareTo(end_data) >0 || ! status.equals("Por aprovar")) {
                    return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                            HttpStatus.BAD_REQUEST);
                } else {
                    activityRepository.save(activity);
                    return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Activity created", idActivity),
                            HttpStatus.CREATED);
                }
            } catch (Exception e) {
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                        HttpStatus.BAD_REQUEST);
            }
        }

    //@PreAuthorize("hasRole('GUARD') or hasRole('MANAGER') or hasRole('NETWORKMAN')")
   // @PutMapping("/activities/{idActivity}")
   // public ResponseEntity<ApiResponse> updateActivitySponsor(@PathVariable long idActivity,) {
     //       Activity activity = activityRepository.findByIdActivity(idActivity);

        //User userLogged = userRepository.findByUserId(currentUser.getId());
        //Set<Role> roleUserLogged = userLogged.getRoles();

        // Get Permissions
        /*if (String.valueOf(roleUserLogged).equals("[Role [id=0]]")
                || String.valueOf(roleUserLogged).equals("[Role [id=1]]")) {
            return alertLogRepository.findAlertLogsByPrison(userLogged.getPrison());
        }*/
    //}
    }

