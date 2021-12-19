package de.hhu.propra.exercise4.controller;

import de.hhu.propra.exercise4.misc.ResponseEntityFactory;
import de.hhu.propra.exercise4.model.entity.Album;
import de.hhu.propra.exercise4.model.entity.Artist;
import de.hhu.propra.exercise4.repository.ArtistRepository;
import de.hhu.propra.exercise4.service.AlbumService;
import de.hhu.propra.exercise4.service.ArtistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/alben")
public class AlbumController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String location = "/alben";

    @Autowired
    AlbumService albumService;

    @GetMapping("")
    public @ResponseBody List<Album> getAlben(@RequestParam(required = false) Integer trackanzahl, @RequestParam(required = false) String bezeichnung) {
        logger.info(String.format("%d %s", trackanzahl, bezeichnung));
        return albumService.getAllAlbumsByFilter(trackanzahl, bezeichnung);
    }

    @PostMapping("")
    @RequestMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity createNewUser(@RequestPart String bezeichnung, @RequestPart String erscheinungsjahr, Principal principal){
        logger.info(String.format("%s %s", erscheinungsjahr, bezeichnung));

        //return ResponseEntityFactory.createPostResponseWithLocation(true, String.format("%s/%s", location, artist.getEmail()), null);
        //    return ResponseEntityFactory.createPostResponseWithLocation(false, location, e);
        return ResponseEntity.ok().build();
    }
}
