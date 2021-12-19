package de.hhu.propra.exercise4.controller;

import de.hhu.propra.exercise4.misc.ResponseEntityFactory;
import de.hhu.propra.exercise4.model.entity.Artist;
import de.hhu.propra.exercise4.repository.ArtistRepository;
import de.hhu.propra.exercise4.service.ArtistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/playlists")
public class PlaylistController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String location = "/playlists";

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    ArtistService artistService;

    @GetMapping("")
    public @ResponseBody List<Artist> getUser(@RequestParam(required = false) String ist_privat, @RequestParam(required = false) String bezeichnung) {
        logger.info("%s %s", ist_privat, bezeichnung);
        return new ArrayList<>();
    }

    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity createNewPlaylist(@RequestPart String bezeichnung, @RequestPart String ist_privat, @RequestPart String coverbild){
        logger.info(String.format("%s %s %s", bezeichnung, ist_privat, coverbild));
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/{playlistid}/titel", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity createNewTitelForPlaylist(@PathVariable int playlistid,  @RequestPart String titel){
        logger.info(String.format("%d %s", playlistid, titel));
        return ResponseEntity.ok().build();
    }
}
