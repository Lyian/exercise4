package de.hhu.propra.exercise4.controller;

import de.hhu.propra.exercise4.misc.ResponseEntityFactory;
import de.hhu.propra.exercise4.model.entity.Artist;
import de.hhu.propra.exercise4.model.entity.User;
import de.hhu.propra.exercise4.repository.ArtistRepository;
import de.hhu.propra.exercise4.repository.CommentRepository;
import de.hhu.propra.exercise4.service.ArtistService;
import de.hhu.propra.exercise4.service.PrinciaplService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/kommentare")
public class CommentController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String location = "/kommentare";

    @Autowired
    PrinciaplService princiaplService;

    @Autowired
    CommentRepository commentRepository;

    @RolesAllowed("USER")
    @PatchMapping("/{kommentarid}")
    public @ResponseBody ResponseEntity updateComment(@PathVariable int kommentarid, @RequestPart String text, Principal principal) {
        logger.info(String.format("updateComment %d, %s", kommentarid, text));

        try{
            User user = princiaplService.loadUserByPrincipal(principal);
            commentRepository.updateComment(user, kommentarid, text);
            return ResponseEntityFactory.createDeleteResponse(true, null);
        }
        catch (Exception e){
            return ResponseEntityFactory.createDeleteResponse(false, e);
        }
    }
}
