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
@RequestMapping(value = "/kommentare")
public class CommentController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String location = "/kommentare";

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    ArtistService artistService;

    @PatchMapping("/{kommentarid}")
    public @ResponseBody List<Artist> updateComment(@PathVariable int kommentarid, @RequestPart String text) {
        logger.info(String.format("updateComment %d, %s",kommentarid, text));
        return new ArrayList<>();
    }
}
