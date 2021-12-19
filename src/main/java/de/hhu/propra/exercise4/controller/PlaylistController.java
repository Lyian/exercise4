package de.hhu.propra.exercise4.controller;

import de.hhu.propra.exercise4.misc.ResponseEntityFactory;
import de.hhu.propra.exercise4.model.entity.Playlist;
import de.hhu.propra.exercise4.model.entity.User;
import de.hhu.propra.exercise4.model.exception.ResourceNotFoundException;
import de.hhu.propra.exercise4.repository.PlaylistRepository;
import de.hhu.propra.exercise4.service.PlaylistService;
import de.hhu.propra.exercise4.service.PrinciaplService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping(value = "/playlists")
public class PlaylistController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String location = "/playlists";

    @Autowired
    PlaylistRepository playlistRepository;

    @Autowired
    PlaylistService playlistService;

    @Autowired
    PrinciaplService princiaplService;

    @GetMapping("")
    public @ResponseBody ResponseEntity<List<Playlist>> getPlaylists(@RequestParam(required = false) String ist_privat, @RequestParam(required = false) String bezeichnung) {
        logger.info(String.format("%s %s", ist_privat, bezeichnung));

        try{
            List<Playlist> playlists = playlistService.getAllPlaylistsByFilter(ist_privat, bezeichnung);
            return ResponseEntityFactory.createGetResponse(playlists, true, null);
        }
        catch(Exception e) {
            return ResponseEntityFactory.createGetResponse(null, false, new ResourceNotFoundException("No playlists found"));
        }
    }

    @RolesAllowed("PREMIUM_USER")
    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity createNewPlaylist(@RequestPart String bezeichnung, @RequestPart String ist_privat, @RequestPart String coverbild, Principal principal){
        logger.info(String.format("%s %s %s", bezeichnung, ist_privat, coverbild));
        try{
            Playlist playlist = new Playlist(0, bezeichnung, ist_privat.toLowerCase(Locale.ROOT).equals("true"), coverbild);
            User user = princiaplService.loadUserByPrincipal(principal);
            int playlistId = playlistRepository.createNewPlaylist(user, playlist);
            return ResponseEntityFactory.createPostResponseWithLocation(true, String.format("%s/%s", location, playlistId), null);
        }
        catch(Exception e){
            return ResponseEntityFactory.createPostResponseWithLocation(false, location, e);
        }
    }

    @RolesAllowed("PREMIUM_USER")
    @PostMapping(value = "/{playlistid}/titel", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity createNewTitelForPlaylist(@PathVariable int playlistid,  @RequestPart String titel, Principal principal){
        logger.info(String.format("%d %s", playlistid, titel));
        try{
            User user = princiaplService.loadUserByPrincipal(principal);
            int titelId = playlistRepository.insertNewTitleToPlaylist(playlistid, Integer.parseInt(titel));
            return ResponseEntityFactory.createPostResponseWithLocation(true, String.format("%s/%s", location, titelId), null);
        }
        catch(Exception e){
            return ResponseEntityFactory.createPostResponseWithLocation(false, location, e);
        }

    }
}
