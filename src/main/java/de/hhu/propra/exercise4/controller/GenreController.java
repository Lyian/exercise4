package de.hhu.propra.exercise4.controller;

import de.hhu.propra.exercise4.misc.ResponseEntityFactory;
import de.hhu.propra.exercise4.model.entity.Genre;
import de.hhu.propra.exercise4.model.exception.ResourceNotFoundException;
import de.hhu.propra.exercise4.repository.GenreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.sqlite.SQLiteDataSource;

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
    SQLiteDataSource dataSource;

    @Autowired
    GenreRepository genreRepository;

    @GetMapping("")
    public @ResponseBody
    List<Genre> getGenre() { return genreRepository.getAllGenres(); }

    @GetMapping(params="bezeichnung")
    public ResponseEntity<List<Genre>> getGenreByDesignation(@RequestParam String bezeichnung) {
        try{
            List<Genre> genres = genreRepository.getGenreByDesignation(bezeichnung);
            return ResponseEntityFactory.createGetResponse(genres, true, null);
        }
        catch(Exception e){
            return  ResponseEntityFactory.createGetResponse(null, false, new ResourceNotFoundException("Genre not found"));
        }
    }

}
