package de.hhu.propra.exercise4.controller;

import de.hhu.propra.exercise4.misc.ResponseEntityFactory;
import de.hhu.propra.exercise4.model.entity.Artist;
import de.hhu.propra.exercise4.repository.ArtistRepository;
import de.hhu.propra.exercise4.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/kuenstler")
public class ArtistController {

    private final String location = "/kuenstler";

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    ArtistService artistService;

    @GetMapping("")
    public @ResponseBody List<Artist> getUser(@RequestParam(required = false) String kuenstlername) {
        List<Artist> artists = artistRepository.getAllArtists();

        if(kuenstlername != null){
            return artists.stream().filter(artist -> artist.getKuenstlername().contains(kuenstlername)).collect(Collectors.toList());
        }

        return artists;
    }

    @PostMapping("")
    @RequestMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity createNewUser(@RequestPart String kuenstlername, @RequestPart String ablaufdatum, @RequestPart String email, @RequestPart String passwort, @RequestPart String benutzername){
        try{
            LocalDate date = LocalDate.parse(ablaufdatum);
            Artist artist = new Artist(0, 0, kuenstlername, date, email, passwort, benutzername);
            artistService.createNewArtist(artist);
            return ResponseEntityFactory.createPostResponseWithLocation(true, String.format("%s/%s", location, artist.getEmail()), null);
        }
        catch(Exception e){
            return ResponseEntityFactory.createPostResponseWithLocation(false, location, e);
        }
    }
}
