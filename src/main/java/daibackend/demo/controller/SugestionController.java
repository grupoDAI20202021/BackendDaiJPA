package daibackend.demo.controller;

import daibackend.demo.model.*;
import daibackend.demo.model.custom.CreateSugestion;
import daibackend.demo.model.custom.updateEmail;
import daibackend.demo.model.custom.updateInt;
import daibackend.demo.payload.response.ApiResponse;
import daibackend.demo.repository.ChildRepository;
import daibackend.demo.repository.SugestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class SugestionController {

    @Autowired
    SugestionRepository sugestionRepository;

    @Autowired
    ChildRepository childRepository;

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping("/sugestions")
    public List<Sugestion> listSugestions(/*@CurrentUser UserPrincipal currentUser*/) {
        return sugestionRepository.findAllByChecked(0);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TOWNHALL') or hasRole('INSTITUTION') or hasROLE('CHILD')")
    @GetMapping("/sugestions/{idSugestion}")
    public Sugestion listSugestionsById(/*@CurrentUser UserPrincipal currentUser*/@PathVariable long idSugestion) {
        return sugestionRepository.findDistinctByIdSugestion(idSugestion);
    }

    @GetMapping("/sugestions/children/{idChild}")
    public List<Sugestion> listSugestionsByChild(/*@CurrentUser UserPrincipal currentUser*/@PathVariable long idChild) {
        Child child = childRepository.findDistinctByIdChild(idChild);
        return sugestionRepository.findAllByChild(child);
    }

   //@PreAuthorize("hasROLE('CHILD')")
    @PostMapping("/sugestions") // Create sugestion
    public ResponseEntity<ApiResponse> saveSugestion(@RequestBody CreateSugestion createSugestion) {
        try {
            Child child = childRepository.findDistinctByIdChild(createSugestion.getIdChild());
            int checked = 0;
            String content= createSugestion.getContent();
            int experience = createSugestion.getExperience();
            Date date = new Date();
            if (child.equals(null)) {
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Criança não existe."),
                        HttpStatus.BAD_REQUEST);
            }

            if (experience != 1 && experience != 2 && experience != 3) {
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Experience inválida."),
                        HttpStatus.BAD_REQUEST);
            }
            Sugestion sugestion = new Sugestion(null,child,content, (byte) 1,date,checked,experience);
            sugestionRepository.save(sugestion);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Sugestion created",sugestion.getIdSugestion()),
                    HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                    HttpStatus.BAD_REQUEST);
        }
    }
   //@PreAuthorize("hasRole('ADMINISTRATOR') or hasROLE('CHILD')") // Child
    @PutMapping("/sugestions/{idSugestion}")
    public ResponseEntity<ApiResponse> updateSugestion(@PathVariable (value="idSugestion")long idSugestion) {
        try {
            if(sugestionRepository.findDistinctByIdSugestion(idSugestion).equals(null)){
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                        HttpStatus.BAD_REQUEST);
            }
            sugestionRepository.updateSugestion(1,idSugestion);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Sugestion updated.", idSugestion),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Invalid data format"),
                    HttpStatus.BAD_REQUEST);
        }
    }
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasROLE('CHILD')")
    @DeleteMapping("sugestions/{idSugestion}")
    public ResponseEntity<ApiResponse> deleteSugestion(@PathVariable (value="idSugestion")long idSugestion) {
        Sugestion sugestion = sugestionRepository.findDistinctByIdSugestion(idSugestion);
        sugestionRepository.delete(sugestion);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Sugestion deleted.", sugestion.getIdSugestion()),
                HttpStatus.CREATED);

    }
}
