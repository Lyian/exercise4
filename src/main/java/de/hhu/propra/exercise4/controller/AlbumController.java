package de.hhu.propra.exercise4.controller;

import de.hhu.propra.exercise4.misc.ResponseEntityFactory;
import de.hhu.propra.exercise4.model.entity.Album;
import de.hhu.propra.exercise4.model.entity.Artist;
import de.hhu.propra.exercise4.model.entity.User;
import de.hhu.propra.exercise4.model.exception.ResourceNotFoundException;
import de.hhu.propra.exercise4.repository.ArtistRepository;
import de.hhu.propra.exercise4.service.AlbumService;
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
@RequestMapping(value = "/alben")
public class AlbumController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String location = "/alben";

    @Autowired
    AlbumService albumService;

    @Autowired
    PrinciaplService princiaplService;

    @GetMapping("")
    public @ResponseBody ResponseEntity<List<Album>> getAlben(@RequestParam(required = false) Integer trackanzahl, @RequestParam(required = false) String bezeichnung) {
        logger.info(String.format("%d %s", trackanzahl, bezeichnung));
        List<Album> albums =  albumService.getAllAlbumsByFilter(trackanzahl, bezeichnung);
        try{
            return ResponseEntityFactory.createGetResponse(albums, true, null);
        }
        catch(Exception e){
            return ResponseEntityFactory.createGetResponse(null, false, new ResourceNotFoundException("No Album found"));
        }
    }

    @RolesAllowed("ARTIST")
    @PostMapping("")
    @RequestMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity createNewUser(@RequestPart String bezeichnung, @RequestPart String erscheinungsjahr, Principal principal){
        logger.info(String.format("%s %s", erscheinungsjahr, bezeichnung));

        Album album = new Album();
        album.setBezeichnung(bezeichnung);
        album.setErscheinungsjahr(Integer.parseInt(erscheinungsjahr));

        try{
            User user = princiaplService.loadUserByPrincipal(principal);
            int albumId = albumService.createNewAlbum(album, user);
            return ResponseEntityFactory.createPostResponseWithLocation(true, String.format("%s/%s", location, albumId), null);
        } catch (Exception e){
            return ResponseEntityFactory.createPostResponseWithLocation(false, location, e);
        }
    }
}
