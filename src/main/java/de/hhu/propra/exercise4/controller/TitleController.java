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

import static java.awt.SystemColor.text;

@RestController
@RequestMapping(value = "/titel")
public class TitleController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String location = "/titel";

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    ArtistService artistService;

    @GetMapping("")
    public @ResponseBody List<Artist> getTitle(@RequestParam(required = false) Integer dauer, @RequestParam(required = false) String bezeichnung) {
        logger.info(String.format("getTitle %d %s", dauer, bezeichnung));
        return new ArrayList<>();
    }

    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
        public ResponseEntity createTitle(@RequestPart String bezeichnung, @RequestPart String dauer, @RequestPart String speicherort_lq, @RequestPart String speicherort_hq){
        logger.info(String.format("createTitle %s %s %s %s", bezeichnung, dauer, speicherort_lq, speicherort_hq));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{titleid}/kommentare")
    public @ResponseBody List<Artist> getComment(@PathVariable int titleid) {
        logger.info(String.format("getComment %d", titleid));
        return new ArrayList<>();
    }

    @PostMapping(value = "/{titelid}/kommentare", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity createComment(@PathVariable int titelid, @RequestPart String text){
        logger.info(String.format("createComment %d %s", titelid, text));
        return ResponseEntity.ok().build();
    }
}
