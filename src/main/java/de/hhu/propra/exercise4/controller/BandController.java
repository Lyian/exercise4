package de.hhu.propra.exercise4.controller;

import de.hhu.propra.exercise4.model.entity.Artist;
import de.hhu.propra.exercise4.repository.ArtistRepository;
import de.hhu.propra.exercise4.service.ArtistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/bands")
public class BandController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String location = "/bands";

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    ArtistService artistService;

    @GetMapping("")
    public @ResponseBody List<Artist> getBands(@RequestParam(required = false) String name, @RequestParam(required = false) String geschichte) {
        logger.info("%s %s", name, geschichte);
        return new ArrayList<>();
    }

    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity createNewBand(@RequestPart String name, @RequestPart String geschichte){
        logger.info(String.format("%s %s", name, geschichte));
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/{bandid}/kuenstler", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity addNewKuenstlerToBand(@PathVariable Integer bandid, @RequestPart String name, @RequestPart String geschichte){
        logger.info(String.format("%d %s %s", bandid, name, geschichte));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{bandid}")
    public ResponseEntity deleteBand(@PathVariable int bandid){
        return ResponseEntity.ok().build();
    }
}
