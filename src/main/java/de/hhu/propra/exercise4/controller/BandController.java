package de.hhu.propra.exercise4.controller;

import de.hhu.propra.exercise4.misc.ResponseEntityFactory;
import de.hhu.propra.exercise4.model.entity.Artist;
import de.hhu.propra.exercise4.model.entity.Band;
import de.hhu.propra.exercise4.model.exception.ResourceNotFoundException;
import de.hhu.propra.exercise4.repository.ArtistRepository;
import de.hhu.propra.exercise4.repository.BandRepository;
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
    BandRepository bandRepository;

    @GetMapping("")
    public @ResponseBody List<Band> getBands() { return bandRepository.getAllBands(); }

    @GetMapping(params="name")
    public ResponseEntity<List<Band>> getBandByName(@RequestParam String name) {
        try{
            List<Band> bands = bandRepository.getAllBandsByName(name);
            return ResponseEntityFactory.createGetResponse(bands, true, null);
        }
        catch(Exception e){
            return ResponseEntityFactory.createGetResponse(null, false, new ResourceNotFoundException("Band not found"));
        }
    }

    @GetMapping(params="geschichte")
    public ResponseEntity<List<Band>> getBandByStory(@RequestParam String geschichte) {
        try{
            List<Band> bands = bandRepository.getAllBandsByStory(geschichte);
            return ResponseEntityFactory.createGetResponse(bands, true, null);
        }
        catch(Exception e){
            return ResponseEntityFactory.createGetResponse(null, false, new ResourceNotFoundException("Band not found"));
        }
    }

    @GetMapping(params= {"geschichte", "name"})
    public ResponseEntity<List<Band>> getBandByStoryAndName(@RequestParam String geschichte, @RequestParam String name) {
        try{
            List<Band> bands = bandRepository.getAllBandsByStoryAndName(geschichte, name);
            return ResponseEntityFactory.createGetResponse(bands, true, null);
        }
        catch(Exception e){
            return ResponseEntityFactory.createGetResponse(null, false, new ResourceNotFoundException("Band not found"));
        }
    }

    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, params = {"name", "geschichte"})
    public ResponseEntity createNewBand(@RequestPart String name, @RequestPart String geschichte){
        try{
            Band band = new Band(0, name, geschichte);
            int key = bandRepository.createNewBand(band);
            return ResponseEntityFactory.createPostResponseWithLocation(true, String.format("%s/%s", location, key), null);
        }
        catch(Exception e){
            return ResponseEntityFactory.createPostResponseWithLocation(false, location, e);
        }
    }

    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, params="name")
    public ResponseEntity createNewBandWoutStory(@RequestPart String name){
        try{
            Band band = new Band(0, name, null);
            int key = bandRepository.createNewBandWoutStory(band);
            return ResponseEntityFactory.createPostResponseWithLocation(true, String.format("%s/%s", location, key), null);
        }
        catch(Exception e){
            return ResponseEntityFactory.createPostResponseWithLocation(false, location, e);
        }
    }

    @PostMapping(value = "/{bandid}/kuenstler", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity addNewKuenstlerToBand(@PathVariable Integer bandid, @RequestPart String kuenstlerid){
        try{
            int key = bandRepository.addNewArtistToBand(bandid, kuenstlerid);
            return ResponseEntityFactory.createPostResponseWithLocation(true, String.format("%s/%s", location, key), null);
        }
        catch(Exception e){
            return ResponseEntityFactory.createPostResponseWithLocation(false, location, e);
        }
    }

    @DeleteMapping("/{bandid}")
    public ResponseEntity deleteBand(@PathVariable int bandid){
        try{
            bandRepository.deleteArtist(bandid);
            return ResponseEntityFactory.createDeleteResponse(true, null);
        }
        catch (Exception e){
            return ResponseEntityFactory.createDeleteResponse(false, e);
        }
    }
}
