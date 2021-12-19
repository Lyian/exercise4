package de.hhu.propra.exercise4.controller;

import de.hhu.propra.exercise4.misc.ResponseEntityFactory;
import de.hhu.propra.exercise4.model.entity.User;
import de.hhu.propra.exercise4.model.exception.ResourceNotFoundException;
import de.hhu.propra.exercise4.repository.UserRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.sqlite.SQLiteDataSource;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/nutzer")
public class UserController {

    private final String location = "/nutzer";

    @Autowired
    SQLiteDataSource dataSource;

    @Autowired
    UserRepository userRepository;

    @RolesAllowed("USER")
    @GetMapping("")
    public @ResponseBody
    List<User> getUser() {
        return userRepository.getAllUsers();
    }

    @GetMapping(params = "email")
    public ResponseEntity<List<User>> getUserByMail(@RequestParam String email) {
        try{
            List<User> users = userRepository.getUserByMail(email);
            return ResponseEntityFactory.createGetResponse(users, true, null);
        }
        catch(Exception e){
            return ResponseEntityFactory.createGetResponse(null, false, new ResourceNotFoundException("User not found"));
        }
    }

    @PostMapping("")
    @RequestMapping( consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity createNewUser(@RequestPart String email, @RequestPart String password, @RequestPart String benutzername){
        try{
            User user = new User(0, email, password, benutzername);
            userRepository.createNewUser(user);
            return ResponseEntityFactory.createPostResponseWithLocation(true, String.format("%s/%s", location, user.getEmail()), null);
        }
        catch(Exception e){
            return ResponseEntityFactory.createPostResponseWithLocation(false, location, e);
        }
    }
}
