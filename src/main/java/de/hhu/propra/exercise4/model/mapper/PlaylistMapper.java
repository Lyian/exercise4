package de.hhu.propra.exercise4.model.mapper;

import de.hhu.propra.exercise4.model.entity.Playlist;
import de.hhu.propra.exercise4.model.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaylistMapper implements RowMapper<Playlist> {

    @Override
    public Playlist mapRow(ResultSet rs, int rowNum) throws SQLException {
        Playlist playlist = new Playlist();
        playlist.setPlaylistid(rs.getInt("ID"));
        playlist.setBezeichnung(rs.getString("Bezeichnung"));
        playlist.setCoverbild(rs.getString("Coverbild"));
        playlist.setIst_privat(rs.getBoolean("Privatflag"));
        return playlist;
    }
}
