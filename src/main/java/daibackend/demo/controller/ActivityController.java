package daibackend.demo.controller;

import daibackend.demo.model.*;
import daibackend.demo.model.custom.*;
import daibackend.demo.payload.response.ApiResponse;
import daibackend.demo.repository.*;
import daibackend.demo.security.CurrentUser;
import daibackend.demo.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    InstitutionRepository institutionRepository;

    @Autowired
    ActivityTypeRepository activityTypeRepository;

        @Autowired
        SponsorRepository sponsorRepository;

    @Autowired
    LoginRepository loginRepository;


        //@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('CHILD')")
        @GetMapping("/activities")
        public List<Activity> listActivities(@CurrentUser UserPrincipal currentUser) {
            return activityRepository.findAllActivities(1);
        }

    //@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TOWNHALL') or hasRole('INSTITUTION') or hasROLE('CHILD')")
    @GetMapping("/activities/{idActivity}")
    public Activity findActivity(@CurrentUser UserPrincipal currentUser,@PathVariable long idActivity) {
        return activityRepository.findByIdActivity(idActivity);
    }


        @PreAuthorize("hasRole('INSTITUTION')")
        @GetMapping("/activities/{idInstitution}/status")
        public List<ActivitiesList> listActivitiesTownHallPorAvaliar(@CurrentUser UserPrincipal currentUser, @PathVariable(value = "idInstitution") long idInstitution) {

             if(currentUser.getId().equals(idInstitution)) {
                return activityRepository.findActivitiesByStatus("Por avaliar", idInstitution, 1);
            }
            return null;
        }

    @PreAuthorize("hasRole('TOWNHALL') or hasRole('INSTITUTION')")
    @GetMapping("/activities/by-townhall/{idTownHall}")
    public List<Activity> listActivitiesTownHall(@CurrentUser UserPrincipal currentUser,@PathVariable(value = "idTownHall") long idTownHall) {
        TownHall t = townHallRepository.findDistinctByIdTownHall(idTownHall);
        Login l = loginRepository.findDistinctByEmailAndActive(currentUser.getEmail(), 1);
        Role roleUserLogged = l.getRole();
        System.out.println(roleUserLogged);
        // Get Permissions
      if (String.valueOf(roleUserLogged).equals("Role [idRole=1]")) {
            if(t.equals(townHallRepository.findDistinctByIdTownHall(currentUser.getId()))) {
                return activityRepository.findActivitiesTownHall(idTownHall,1);
            }
        }
        if (String.valueOf(roleUserLogged).equals("Role [idRole=2]")) {
            if(t.equals(townHallRepository.findDistinctByIdTownHall(institutionRepository.getTownhallId(currentUser.getId())))) {
                return activityRepository.findActivitiesTownHall(idTownHall,1);
            }
        }

        return null;
    }

    //@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TOWNHALL') or hasRole('INSTITUTION') or hasROLE('CHILD')")
    @GetMapping("/activities/current/{idActivityType}")
    public int listCurrentActivitiesTotal(@CurrentUser UserPrincipal currentUser,@PathVariable long idActivityType) {
        return activityRepository.findActivitiesByStatusNumber("Aprovada", idActivityType,1);
    }

        @PreAuthorize("hasRole('INSTITUTION')")
        @PostMapping("/activities")
        public ResponseEntity<ApiResponse> saveActivity(@CurrentUser UserPrincipal currentUser,@RequestBody CreateActivity activity) {
            try {
                // Activity Attributes


                Date init_data  = activity.getInit_data();
                Date end_data  = activity.getEnd_data();
                String title = activity.getTitle();
                String status = "Por aprovar";
                String address = activity.getAddress();
                int spaces = activity.getSpaces();
                Institution institution = institutionRepository.findDistinctByIdInstitution(activity.getIdInstitution());
                ActivityType activityType = activityTypeRepository.findDistinctByIdActivityType(activity.getIdActivityType());

                if(!currentUser.getId().equals(activity.getIdInstitution())){
                    return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Não tem acesso."),
                            HttpStatus.BAD_REQUEST);
                }

                if (init_data.compareTo(end_data) >0 || ! status.equals("Por aprovar")) {
                    return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                            HttpStatus.BAD_REQUEST);
                } else {
                    Activity Activity = new Activity(null,institution,activityType,null,status,init_data,end_data,address,title,0,spaces);
                    activityRepository.save(Activity);
                    return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Activity created", Activity.getIdActivity()),
                            HttpStatus.CREATED);
                }
            } catch (Exception e) {
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                        HttpStatus.BAD_REQUEST);
            }
        }

    @PreAuthorize("hasRole('TOWNHALL')")  // TownHall
    @PutMapping("/activities/{idActivity}/sponsor")
    public ResponseEntity<ApiResponse> updateActivitySponsor(@PathVariable (value="idActivity")long idActivity, @RequestBody updateActivityTownHall update) {
        try {
    if(activityRepository.findByIdActivity(idActivity).equals(null)){
        return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                HttpStatus.BAD_REQUEST);
    }

        long idSponsor = update.getIdSponsor();

        String status = "Aprovada";

           activityRepository.updateActivityAsTownHall(idSponsor,status, idActivity);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Activity updated.", idActivity),
                    HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('INSTITUTION')")  //Institution
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
            String title = update.getTitle();

            activityRepository.updateActivityAsInstituition(address,init_data,end_data,spaces,title,idActivity);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Activity updated.", idActivity),
                    HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('INSTITUTION') ") // Institution
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

    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TOWNHALL') or hasRole('INSTITUTION') or hasROLE('CHILD')")
    @GetMapping("/activitiesyear")
    public ActivitiesInYear listActivitiesYear(/*@CurrentUser UserPrincipal currentUser*/) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = simpleDateFormat.parse("2021-01-01");
            date2 = simpleDateFormat.parse("2021-01-31");
            int jan = activityRepository.findAllByInit_dataBetween(date1,date2,1);
            date1 = simpleDateFormat.parse("2021-02-01");
            date2 = simpleDateFormat.parse("2021-02-28");
            int feb = activityRepository.findAllByInit_dataBetween(date1,date2,1);
            date1 = simpleDateFormat.parse("2021-03-01");
            date2 = simpleDateFormat.parse("2021-03-31");
            int mar= activityRepository.findAllByInit_dataBetween(date1,date2,1);
            date1 = simpleDateFormat.parse("2021-04-01");
            date2 = simpleDateFormat.parse("2021-04-30");
            int apr= activityRepository.findAllByInit_dataBetween(date1,date2,1);
            date1 = simpleDateFormat.parse("2021-05-01");
            date2 = simpleDateFormat.parse("2021-05-31");
            int may= activityRepository.findAllByInit_dataBetween(date1,date2,1);
            date1 = simpleDateFormat.parse("2021-06-01");
            date2 = simpleDateFormat.parse("2021-06-30");
            int jun= activityRepository.findAllByInit_dataBetween(date1,date2,1);
            date1 = simpleDateFormat.parse("2021-07-01");
            date2 = simpleDateFormat.parse("2021-07-31");
            int jul= activityRepository.findAllByInit_dataBetween(date1,date2,1);
            date1 = simpleDateFormat.parse("2021-08-01");
            date2 = simpleDateFormat.parse("2021-08-31");
            int aug= activityRepository.findAllByInit_dataBetween(date1,date2,1);
            date1 = simpleDateFormat.parse("2021-09-01");
            date2 = simpleDateFormat.parse("2021-09-30");
            int sep= activityRepository.findAllByInit_dataBetween(date1,date2,1);
            date1 = simpleDateFormat.parse("2021-10-01");
            date2 = simpleDateFormat.parse("2021-10-31");
            int oct= activityRepository.findAllByInit_dataBetween(date1,date2,1);
            date1 = simpleDateFormat.parse("2021-11-01");
            date2 = simpleDateFormat.parse("2021-11-30");
            int nov= activityRepository.findAllByInit_dataBetween(date1,date2,1);
            date1 = simpleDateFormat.parse("2021-12-01");
            date2 = simpleDateFormat.parse("2021-12-31");
            int dec= activityRepository.findAllByInit_dataBetween(date1,date2,1);
            ActivitiesInYear activitiesInYear = new ActivitiesInYear(jan,feb,mar,apr,may,jun,jul,aug,sep,oct,nov,dec);
            return activitiesInYear;
        } catch (ParseException e) {
            e.printStackTrace();
        }

      return null;
    }

    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TOWNHALL') or hasRole('INSTITUTION') or hasROLE('CHILD')")
    @GetMapping("/lastactivities")
    public List<ActivityList> listActivitiesLast(/*@CurrentUser UserPrincipal currentUser*/) {
        return activityRepository.findLast8( 1,PageRequest.of(0,8));
    }
    }


