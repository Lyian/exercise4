package de.hhu.propra.exercise4.controller;

import de.hhu.propra.exercise4.misc.ResponseEntityFactory;
import de.hhu.propra.exercise4.model.entity.PremiumUser;
import de.hhu.propra.exercise4.model.entity.User;
import de.hhu.propra.exercise4.model.exception.ResourceNotFoundException;
import de.hhu.propra.exercise4.repository.PremiumUserRepository;
import de.hhu.propra.exercise4.repository.UserRepository;
import de.hhu.propra.exercise4.service.PremiumUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.sqlite.SQLiteDataSource;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/premiumnutzer")
public class PremiumUserController {

    private final String location = "/premiumnutzer";

    @Autowired
    SQLiteDataSource dataSource;

    @Autowired
    PremiumUserRepository premiumUserRepository;

    @Autowired
    PremiumUserService premiumUserService;

    @GetMapping("")
    public @ResponseBody
    List<PremiumUser> getUser() {
        return premiumUserRepository.getAllUsers();
    }

    @GetMapping(params = "abgelaufen")
    public @ResponseBody
    List<PremiumUser> getUser(@RequestParam String abgelaufen) {
        return premiumUserService.getAllPremiumUsersByValidity(abgelaufen);
    }

    @PostMapping("")
    @RequestMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity createNewUser(@RequestPart String ablaufdatum, @RequestPart String email, @RequestPart String passwort, @RequestPart String benutzername){
        LocalDate localDate = LocalDate.parse(ablaufdatum);

        try{
            PremiumUser premiumUser = new PremiumUser(0,0, localDate, email,passwort,benutzername);
            premiumUserService.createNewPremiumUser(premiumUser);
            return ResponseEntityFactory.createPostResponseWithLocation(true, String.format("%s/%s", location, premiumUser.getEmail()), null);
        }
        catch(Exception e){
            return ResponseEntityFactory.createPostResponseWithLocation(false, location, e);
        }
    }
}
