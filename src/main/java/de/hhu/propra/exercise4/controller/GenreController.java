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
@RequestMapping(value = "/genres")
public class GenreController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String location = "/genres";

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    ArtistService artistService;

    @GetMapping("")
    public @ResponseBody List<Artist> getGenre(@RequestParam(required = false) String bezeichnung) {
        logger.info(String.format("getGenre %s", bezeichnung));
        return new ArrayList<>();
    }
}
