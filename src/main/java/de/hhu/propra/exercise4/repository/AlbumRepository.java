package de.hhu.propra.exercise4.repository;

import de.hhu.propra.exercise4.model.entity.Album;
import de.hhu.propra.exercise4.model.entity.Artist;
import de.hhu.propra.exercise4.model.entity.User;
import de.hhu.propra.exercise4.model.mapper.AlbumMapper;
import de.hhu.propra.exercise4.model.mapper.UserMapper;
import de.hhu.propra.exercise4.repository.helpers.QueryHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public class AlbumRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<Album> getAllAlbenWithTracks(){
        return jdbcTemplate.query("Select ID, Name, Jahr, Count(ttva.AlbumID) as Tracks from Album join titel_teil_von_album ttva on Album.ID = ttva.AlbumID group by AlbumID", new AlbumMapper());
    }

    public Integer createNewAlbum(Album album) throws Exception {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Album (Name, Jahr) VALUES(?,?)");
            ps.setString(1, album.getBezeichnung());
            ps.setString(2, String.format("%s-01-01", album.getErscheinungsjahr()));
            return ps;
        }, keyHolder);

        return (int) keyHolder.getKey();
    }

    public Integer insertNewAlbumForArtist(User user, Album album) throws SQLException{
        return jdbcTemplate.update("INSERT INTO Kuenstler_macht_album (Email, AlbumID) VALUES (?,?)", user.getEmail(), album.getAlbumdid());
    }
}
