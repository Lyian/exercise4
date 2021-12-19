package de.hhu.propra.exercise4.model.mapper;

import de.hhu.propra.exercise4.model.entity.Album;
import de.hhu.propra.exercise4.model.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class AlbumMapper implements RowMapper<Album> {

    @Override
    public Album mapRow(ResultSet rs, int rowNum) throws SQLException {
        Album album = new Album();
        album.setAlbumdid(rs.getInt("ID"));
        album.setBezeichnung(rs.getString("Name"));
        album.setErscheinungsjahr(convertLocalDateToYear(rs.getString("Jahr")));
        album.setTracks(rs.getInt("Tracks"));
        return album;
    }

    private int convertLocalDateToYear(String dateString){
        return LocalDate.parse(dateString).getYear();
    }
}
