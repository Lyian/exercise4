package de.hhu.propra.exercise4.repository;

import de.hhu.propra.exercise4.model.entity.Artist;
import de.hhu.propra.exercise4.repository.ArtistRepository;
import de.hhu.propra.exercise4.model.entity.Band;
import de.hhu.propra.exercise4.model.entity.User;
import de.hhu.propra.exercise4.model.mapper.BandMapper;
import de.hhu.propra.exercise4.model.mapper.UserMapper;
import de.hhu.propra.exercise4.repository.helpers.QueryHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class BandRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    ArtistRepository artistRepository;

    public List<Band> getAllBands(){
        return jdbcTemplate.query("SELECT ROWID, * FROM Band", new BandMapper());
    }

    public List<Band> getAllBandsByName(String name) throws Exception {
        return jdbcTemplate.query("SELECT ROWID, * FROM Band WHERE Name LIKE ?", new BandMapper(), QueryHelper.createLikeParam(name));
    }

    public List<Band> getAllBandsByStory(String story) throws Exception {
        return jdbcTemplate.query("SELECT ROWID, * FROM Band WHERE Geschichte LIKE ?", new BandMapper(), QueryHelper.createLikeParam(story));
    }

    public List<Band> getAllBandsByStoryAndName(String story, String name) throws Exception {
        return jdbcTemplate.query("SELECT ROWID, * FROM Band WHERE Geschichte LIKE ? AND Name Like ?", new BandMapper(), QueryHelper.createLikeParam(story), QueryHelper.createLikeParam(name));
    }

    public Integer createNewBand(Band band) throws Exception {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Band (Name, Geschichte) VALUES(?,?)");
            ps.setString(1, band.getName());
            ps.setString(2, band.getGeschichte());
            return ps;
        }, keyHolder);
        return (int) keyHolder.getKey();
    }

    public Integer createNewBandWoutStory(Band band) throws Exception {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Band (Name) VALUES(?)");
            ps.setString(1, band.getName());
            return ps;
        }, keyHolder);
        return (int) keyHolder.getKey();
    }

    public Integer addNewArtistToBand(Integer bandID, String artistID) throws Exception {
        try{
            List<Artist> artists = artistRepository.getArtistByRowid(artistID);
            Artist artist = artists.get(0);
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO Kuenstler_teil_von_band (Email, BandID) VALUES (?, ?)");
                ps.setString(1, artist.getEmail());
                ps.setString(2, Integer.toString(bandID));
                return ps;
            }, keyHolder);
            return (int) keyHolder.getKey();
        }
        catch (Exception e) {
            throw e;
        }


    }
}
