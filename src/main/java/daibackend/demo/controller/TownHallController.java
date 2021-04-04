package daibackend.demo.controller;

import daibackend.demo.model.custom.TownHallList;
import daibackend.demo.repository.TownHallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class TownHallController {
    @Autowired
    TownHallRepository townHallRepository;

    //@PreAuthorize("hasRole('GUARD') or hasRole('MANAGER') or hasRole('NETWORKMAN')")
    @GetMapping("/townhalls")
    public List<TownHallList> listTownHalls(/*@CurrentUser UserPrincipal currentUser*/) {
        //User userLogged = userRepository.findByUserId(currentUser.getId());
        //Set<Role> roleUserLogged = userLogged.getRoles();

        // Get Permissions
        /*if (String.valueOf(roleUserLogged).equals("[Role [id=0]]")
                || String.valueOf(roleUserLogged).equals("[Role [id=1]]")) {
            return alertLogRepository.findAlertLogsByPrison(userLogged.getPrison());
        }*/
        return townHallRepository.findAllTownHallList();
    }
}
