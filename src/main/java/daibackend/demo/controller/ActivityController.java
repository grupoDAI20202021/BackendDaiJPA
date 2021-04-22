package daibackend.demo.controller;

import daibackend.demo.model.Activity;
import daibackend.demo.model.Institution;
import daibackend.demo.model.Sponsor;
import daibackend.demo.model.TownHall;
import daibackend.demo.model.custom.*;
import daibackend.demo.payload.response.ApiResponse;
import daibackend.demo.repository.ActivityRepository;
import daibackend.demo.repository.SponsorRepository;
import daibackend.demo.repository.TownHallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.text.*;
import java.util.Calendar;
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

        @Autowired
        SponsorRepository sponsorRepository;


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
    @GetMapping("/activities/{idActivity}")
    public Activity findActivity(/*@CurrentUser UserPrincipal currentUser*/@PathVariable long idActivity) {
        //User userLogged = userRepository.findByUserId(currentUser.getId());
        //Set<Role> roleUserLogged = userLogged.getRoles();

        // Get Permissions
        /*if (String.valueOf(roleUserLogged).equals("[Role [id=0]]")
                || String.valueOf(roleUserLogged).equals("[Role [id=1]]")) {
            return alertLogRepository.findAlertLogsByPrison(userLogged.getPrison());
        }*/
        return activityRepository.findByIdActivity(idActivity);
    }


        //@PreAuthorize("hasRole('GUARD') or hasRole('MANAGER') or hasRole('NETWORKMAN')")
        @GetMapping("/activities/{idInstitution}/status")
        public List<ActivitiesList> listActivitiesTownHallPorAvaliar(@PathVariable(value = "idInstitution") long idInstitution) {

            //User userLogged = userRepository.findByUserId(currentUser.getId());
            //Set<Role> roleUserLogged = userLogged.getRoles();

            // Get Permissions
        /*if (String.valueOf(roleUserLogged).equals("[Role [id=0]]")
                || String.valueOf(roleUserLogged).equals("[Role [id=1]]")) {
            return alertLogRepository.findAlertLogsByPrison(userLogged.getPrison());
        }*/
            return activityRepository.findActivitiesByStatus("Por avaliar",idInstitution);
        }

    //@PreAuthorize("hasRole('GUARD') or hasRole('MANAGER') or hasRole('NETWORKMAN')")
    @GetMapping("/activities/by-townhall/{idTownHall}")
    public List<Activity> listActivitiesTownHall(@PathVariable(value = "idTownHall") long idTownHall) {
        TownHall t = townHallRepository.findDistinctByIdTownHall(idTownHall);

        //User userLogged = userRepository.findByUserId(currentUser.getId());
        //Set<Role> roleUserLogged = userLogged.getRoles();

        // Get Permissions
        /*if (String.valueOf(roleUserLogged).equals("[Role [id=0]]")
                || String.valueOf(roleUserLogged).equals("[Role [id=1]]")) {
            return alertLogRepository.findAlertLogsByPrison(userLogged.getPrison());
        }*/
        return activityRepository.findActivitiesTownHall(idTownHall);
    }

    //@PreAuthorize("hasRole('GUARD') or hasRole('MANAGER') or hasRole('NETWORKMAN')")
    @GetMapping("/activities/current/{idActivityType}")
    public int listCurrentActivitiesTotal(/*@CurrentUser UserPrincipal currentUser*/@PathVariable long idActivityType) {
        //User userLogged = userRepository.findByUserId(currentUser.getId());
        //Set<Role> roleUserLogged = userLogged.getRoles();

        // Get Permissions
        /*if (String.valueOf(roleUserLogged).equals("[Role [id=0]]")
                || String.valueOf(roleUserLogged).equals("[Role [id=1]]")) {
            return alertLogRepository.findAlertLogsByPrison(userLogged.getPrison());
        }*/
        return activityRepository.findActivitiesByStatusNumber("Aprovada", idActivityType);
    }

        @PostMapping("/activities")
        public ResponseEntity<ApiResponse> saveActivity(@RequestBody Activity activity) {
            try {
                // Activity Attributes

                Long idActivity = activity.getIdActivity();
                Date init_data  = activity.getInit_data();
                Date end_data  = activity.getEnd_data();
                String title = activity.getTitle();
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

    //@PreAuthorize("hasRole('GUARD') or hasRole('MANAGER') or hasRole('NETWORKMAN')")  // TownHall
    @PutMapping("/activities/{idActivity}/sponsor")
    public ResponseEntity<ApiResponse> updateActivitySponsor(@PathVariable (value="idActivity")long idActivity, @RequestBody updateActivityTownHall update) {
        try {
    if(activityRepository.findByIdActivity(idActivity).equals(null)){
        return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                HttpStatus.BAD_REQUEST);
    }

        long idSponsor = update.getSponsor().getIdSponsor();
        Date init_data = update.getInit_data();
        Date end_data = update.getEnd_data();
        String status = "Aprovada";

           activityRepository.updateActivityAsTownHall(idSponsor,init_data,end_data,idActivity,status);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Activity updated.", idActivity),
                    HttpStatus.CREATED);
        //User userLogged = userRepository.findByUserId(currentUser.getId());
        //Set<Role> roleUserLogged = userLogged.getRoles();

        // Get Permissions
        /*if (String.valueOf(roleUserLogged).equals("[Role [id=0]]")
                || String.valueOf(roleUserLogged).equals("[Role [id=1]]")) {
            return alertLogRepository.findAlertLogsByPrison(userLogged.getPrison());
        }*/
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    //@PreAuthorize("hasRole('GUARD') or hasRole('MANAGER') or hasRole('NETWORKMAN')")  Institution
    @PutMapping("/activities/{idActivity}")
    public ResponseEntity<ApiResponse> updateActivity(@PathVariable (value="idActivity")long idActivity, @RequestBody updateActivityInstitution update) {
        try {
            if(activityRepository.findByIdActivity(idActivity).equals(null)){
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                        HttpStatus.BAD_REQUEST);
            }

            int spaces = update.getSpaces();
            Date init_data = update.getInit_data();
            Date end_data = update.getEnd_data();
            String address = update.getAddress();

            activityRepository.updateActivityAsInstituition(address,init_data,end_data,spaces,idActivity);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Activity updated.", idActivity),
                    HttpStatus.CREATED);
            //User userLogged = userRepository.findByUserId(currentUser.getId());
            //Set<Role> roleUserLogged = userLogged.getRoles();

            // Get Permissions
        /*if (String.valueOf(roleUserLogged).equals("[Role [id=0]]")
                || String.valueOf(roleUserLogged).equals("[Role [id=1]]")) {
            return alertLogRepository.findAlertLogsByPrison(userLogged.getPrison());
        }*/
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    //@PreAuthorize("hasRole('GUARD') or hasRole('MANAGER') or hasRole('NETWORKMAN')")  Institution
    @PutMapping("/activities/{idActivity}/evaluation")
    public ResponseEntity<ApiResponse> updateActivityEvaluation(@PathVariable (value="idActivity")long idActivity, @RequestBody updateActivityEvaluation update) {
        try {
            if(activityRepository.findByIdActivity(idActivity).equals(null)){
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                        HttpStatus.BAD_REQUEST);
            }
            int evaluation= update.getEvaluation();
            activityRepository.updateActivityEvaluation(evaluation,"Finalizada",idActivity);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Activity updated.", idActivity),
                    HttpStatus.CREATED);
            //User userLogged = userRepository.findByUserId(currentUser.getId());
            //Set<Role> roleUserLogged = userLogged.getRoles();

            // Get Permissions
        /*if (String.valueOf(roleUserLogged).equals("[Role [id=0]]")
                || String.valueOf(roleUserLogged).equals("[Role [id=1]]")) {
            return alertLogRepository.findAlertLogsByPrison(userLogged.getPrison());
        }*/
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/activities/{idActivity}")
    public ResponseEntity<ApiResponse> deleteActivity(@PathVariable (value="idActivity")long idActivity) {
        try {
        Activity activity = activityRepository.findByIdActivity(idActivity);
        activityRepository.delete(activity);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Activity deleted.", idActivity),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    //@PreAuthorize("hasRole('GUARD') or hasRole('MANAGER') or hasRole('NETWORKMAN')")
    @GetMapping("/activitiesyear")
    public ActivitiesInYear listActivitiesYear(/*@CurrentUser UserPrincipal currentUser*/) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = simpleDateFormat.parse("2021-01-01");
            date2 = simpleDateFormat.parse("2021-01-31");
            int jan = activityRepository.findAllByInit_dataBetween(date1,date2);
            date1 = simpleDateFormat.parse("2021-02-01");
            date2 = simpleDateFormat.parse("2021-02-28");
            int feb = activityRepository.findAllByInit_dataBetween(date1,date2);
            date1 = simpleDateFormat.parse("2021-03-01");
            date2 = simpleDateFormat.parse("2021-03-31");
            int mar= activityRepository.findAllByInit_dataBetween(date1,date2);
            date1 = simpleDateFormat.parse("2021-04-01");
            date2 = simpleDateFormat.parse("2021-04-30");
            int apr= activityRepository.findAllByInit_dataBetween(date1,date2);
            date1 = simpleDateFormat.parse("2021-05-01");
            date2 = simpleDateFormat.parse("2021-05-31");
            int may= activityRepository.findAllByInit_dataBetween(date1,date2);
            date1 = simpleDateFormat.parse("2021-06-01");
            date2 = simpleDateFormat.parse("2021-06-30");
            int jun= activityRepository.findAllByInit_dataBetween(date1,date2);
            date1 = simpleDateFormat.parse("2021-07-01");
            date2 = simpleDateFormat.parse("2021-07-31");
            int jul= activityRepository.findAllByInit_dataBetween(date1,date2);
            date1 = simpleDateFormat.parse("2021-08-01");
            date2 = simpleDateFormat.parse("2021-08-31");
            int aug= activityRepository.findAllByInit_dataBetween(date1,date2);
            date1 = simpleDateFormat.parse("2021-09-01");
            date2 = simpleDateFormat.parse("2021-09-30");
            int sep= activityRepository.findAllByInit_dataBetween(date1,date2);
            date1 = simpleDateFormat.parse("2021-10-01");
            date2 = simpleDateFormat.parse("2021-10-31");
            int oct= activityRepository.findAllByInit_dataBetween(date1,date2);
            date1 = simpleDateFormat.parse("2021-11-01");
            date2 = simpleDateFormat.parse("2021-11-30");
            int nov= activityRepository.findAllByInit_dataBetween(date1,date2);
            date1 = simpleDateFormat.parse("2021-12-01");
            date2 = simpleDateFormat.parse("2021-12-31");
            int dec= activityRepository.findAllByInit_dataBetween(date1,date2);
            ActivitiesInYear activitiesInYear = new ActivitiesInYear(jan,feb,mar,apr,may,jun,jul,aug,sep,oct,nov,dec);
            return activitiesInYear;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //User userLogged = userRepository.findByUserId(currentUser.getId());
        //Set<Role> roleUserLogged = userLogged.getRoles();

        // Get Permissions
        /*if (String.valueOf(roleUserLogged).equals("[Role [id=0]]")
                || String.valueOf(roleUserLogged).equals("[Role [id=1]]")) {
            return alertLogRepository.findAlertLogsByPrison(userLogged.getPrison());
        }*/
      return null;
    }

    //@PreAuthorize("hasRole('GUARD') or hasRole('MANAGER') or hasRole('NETWORKMAN')")
    @GetMapping("/lastactivities")
    public List<ActivityList> listActivitiesLast(/*@CurrentUser UserPrincipal currentUser*/) {
        //User userLogged = userRepository.findByUserId(currentUser.getId());
        //Set<Role> roleUserLogged = userLogged.getRoles();

        // Get Permissions
        /*if (String.valueOf(roleUserLogged).equals("[Role [id=0]]")
                || String.valueOf(roleUserLogged).equals("[Role [id=1]]")) {
            return alertLogRepository.findAlertLogsByPrison(userLogged.getPrison());
        }*/
        return activityRepository.findLast8( PageRequest.of(0,8));
    }
    }


