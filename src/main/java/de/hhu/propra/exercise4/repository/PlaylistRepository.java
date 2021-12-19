package de.hhu.propra.exercise4.repository;

import de.hhu.propra.exercise4.model.entity.Album;
import de.hhu.propra.exercise4.model.entity.Playlist;
import de.hhu.propra.exercise4.model.entity.User;
import de.hhu.propra.exercise4.model.mapper.AlbumMapper;
import de.hhu.propra.exercise4.model.mapper.PlaylistMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PlaylistRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Playlist> getAllPlaylists() throws Exception {
        return jdbcTemplate.query("Select ID, Privatflag, Bezeichnung, Coverbild from Playlist", new PlaylistMapper());
    }

    public Integer createNewPlaylist(User user, Playlist playlist) throws Exception {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Playlist (Privatflag, Bezeichnung, Coverbild, Email) VALUES (?,?,?,?)");
            ps.setBoolean(1, playlist.isIst_privat());
            ps.setString(2, playlist.getBezeichnung());
            ps.setString(3, playlist.getCoverbild());
            ps.setString(4, user.getEmail());
            return ps;
        }, keyHolder);

        return (int) keyHolder.getKey();
    }

    public Integer insertNewTitleToPlaylist(int playlistId, int titelId) throws Exception {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO titel_ist_in_playlist (TitelID, PlaylistID) VALUES (?,?)");
            ps.setInt(1, titelId);
            ps.setInt(2, playlistId);
            return ps;
        }, keyHolder);

        return (int) keyHolder.getKey();
    }
}
