package de.hhu.propra.exercise4.model.mapper;

import de.hhu.propra.exercise4.model.entity.Artist;
import de.hhu.propra.exercise4.model.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ArtistMapper implements RowMapper<Artist> {

    @Override
    public Artist mapRow(ResultSet rs, int rowNum) throws SQLException {
        Artist artist = new Artist();
        artist.setPremiumnutzerid(rs.getInt("Premiumnutzerid"));
        artist.setNutzerid(rs.getInt("Nutzerid"));
        artist.setKuenstlername(rs.getString("Name"));
        artist.setBenutzername(rs.getString("Benutzername"));
        artist.setAblaufdatum(dateToLocalDate(rs.getDate("Datum")));
        artist.setPasswort(rs.getString("Passwort"));
        artist.setEmail(rs.getString("Email"));
        return artist;
    }

    private LocalDate dateToLocalDate(Date dateToConvert){
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
