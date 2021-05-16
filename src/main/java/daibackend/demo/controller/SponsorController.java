package daibackend.demo.controller;

import daibackend.demo.model.*;
import daibackend.demo.model.custom.CreateSponsor;
import daibackend.demo.model.custom.SponsorList;
import daibackend.demo.payload.response.ApiResponse;
import daibackend.demo.repository.SponsorRepository;
import daibackend.demo.repository.TownHallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class SponsorController {
    @Autowired
    SponsorRepository sponsorRepository;

    @Autowired
    TownHallRepository townHallRepository;

    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TOWNHALL') or hasRole('INSTITUTION')")
    @GetMapping("/townhalls/{idTownHall}/sponsors")
    public List<SponsorList> listSponsor(/*@CurrentUser UserPrincipal currentUser*/@PathVariable long idTownHall) {
        return sponsorRepository.findAllByTownHall(idTownHall);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TOWNHALL')")
    @PostMapping("/townhalls/{idTownHall}/sponsors") // Creat Sponsor
    public ResponseEntity<ApiResponse> saveSponsor(@PathVariable long idTownHall, @RequestBody CreateSponsor createSponsor) {
        try {
            TownHall townHall = townHallRepository.findDistinctByIdTownHall(idTownHall);
            String name = createSponsor.getName();
            Date date = new Date();
            if (sponsorRepository.existsByName(createSponsor.getName())) {
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Patrocinador ja existente."),
                        HttpStatus.BAD_REQUEST);
            }

            Sponsor sponsor = new Sponsor(null ,townHall,name,null,date,1);
            sponsorRepository.save(sponsor);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Sponsor created",sponsor.getIdSponsor()),
                    HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                    HttpStatus.BAD_REQUEST);
        }
    }


    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TOWNHALL')") // Deactivate
    @PutMapping("sponsors/{idSponsor}")
    public ResponseEntity<ApiResponse> deleteLogic(@PathVariable (value="idSponsor")long idSponsor) {
        try {
            if(sponsorRepository.findDistinctByIdSponsor(idSponsor).equals(null)){
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                        HttpStatus.BAD_REQUEST);
            }

           sponsorRepository.deleteLogic(0,idSponsor);

            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Sponsor eliminated.", idSponsor),
                    HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TOWNHALL')")
    @DeleteMapping("sponsors/{idSponsor}")
    public ResponseEntity<ApiResponse> deleteSponsor(@PathVariable (value="idSponsor")long idSponsor) {
        Sponsor sponsor = sponsorRepository.findDistinctByIdSponsor(idSponsor);
        sponsorRepository.delete(sponsor);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Sponsor deleted.", sponsor.getIdSponsor()),
                HttpStatus.CREATED);

    }
}
