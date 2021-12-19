package de.hhu.propra.exercise4.controller;

import de.hhu.propra.exercise4.misc.ResponseEntityFactory;
import de.hhu.propra.exercise4.model.entity.Artist;
import de.hhu.propra.exercise4.model.entity.Comment;
import de.hhu.propra.exercise4.model.entity.Title;
import de.hhu.propra.exercise4.model.entity.User;
import de.hhu.propra.exercise4.model.exception.ResourceNotFoundException;
import de.hhu.propra.exercise4.repository.ArtistRepository;
import de.hhu.propra.exercise4.repository.CommentRepository;
import de.hhu.propra.exercise4.service.PrinciaplService;
import de.hhu.propra.exercise4.service.TitleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/titel")
public class TitleController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String location = "/titel";

    @Autowired
    PrinciaplService princiaplService;

    @Autowired
    TitleService titleService;

    @Autowired
    CommentRepository commentRepository;

    @GetMapping("")
    public @ResponseBody ResponseEntity<List<Title>> getTitle(@RequestParam(required = false) Integer dauer, @RequestParam(required = false) String bezeichnung) {
        logger.info(String.format("getTitle %d %s", dauer, bezeichnung));
        try{
            List<Title> titles = titleService.getAllTitlesByFilter(dauer, bezeichnung);
            return ResponseEntityFactory.createGetResponse(titles, true, null);
        }
        catch(Exception e){
            return ResponseEntityFactory.createGetResponse(null, false, new ResourceNotFoundException("No title found"));
        }
    }

    @RolesAllowed("ARTIST")
    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
        public ResponseEntity createTitle(@RequestPart String bezeichnung, @RequestPart String dauer, @RequestPart String speicherort_lq, @RequestPart String speicherort_hq, Principal principal) {
        logger.info(String.format("createTitle %s %s %s %s", bezeichnung, dauer, speicherort_lq, speicherort_hq));

        Title title = new Title(0, bezeichnung, Integer.parseInt(dauer), speicherort_lq, speicherort_hq);
        try{
            User user = princiaplService.loadUserByPrincipal(principal);
            int titleId = titleService.createNewTitleForUser(title, user);
            return ResponseEntityFactory.createPostResponseWithLocation(true, String.format("%s/%s", location, titleId), null);
        }catch(Exception e){
            return ResponseEntityFactory.createPostResponseWithLocation(false, location, e);
        }
    }


    @GetMapping("/{titleid}/kommentare")
    public @ResponseBody ResponseEntity<List<Comment>> getComment(@PathVariable int titleid) {
        logger.info(String.format("getComment %d", titleid));

        try{
            Title title = new Title();
            title.setTitleid(titleid);
            List<Comment> comments = commentRepository.getAllCommentsByTitleId(title);
            return ResponseEntityFactory.createGetResponse(comments, true, null);
        }
        catch(Exception e){
            return ResponseEntityFactory.createGetResponse(null, false, new ResourceNotFoundException("No comments found"));
        }
    }

    @RolesAllowed("USER")
    @PostMapping(value = "/{titelid}/kommentare", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity createComment(@PathVariable int titelid, @RequestPart String text, Principal principal){
        logger.info(String.format("createComment %d %s", titelid, text));
        try{
            User user = princiaplService.loadUserByPrincipal(principal);
            int titleId = commentRepository.createComment(user, titelid, text);
            return ResponseEntityFactory.createPostResponseWithLocation(true, String.format("%s/%s", location, titleId), null);
        }catch(Exception e){
            return ResponseEntityFactory.createPostResponseWithLocation(false, location, e);
        }
    }
}
