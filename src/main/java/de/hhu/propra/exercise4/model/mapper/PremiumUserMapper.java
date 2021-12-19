package de.hhu.propra.exercise4.model.mapper;

import de.hhu.propra.exercise4.model.entity.PremiumUser;
import de.hhu.propra.exercise4.model.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class PremiumUserMapper implements RowMapper<PremiumUser> {

    @Override
    public PremiumUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        PremiumUser premiumUser = new PremiumUser();
        premiumUser.setPremiumnutzerid(rs.getInt("PremiumnutzerId"));
        premiumUser.setNutzerid(rs.getInt("NutzerId"));
        premiumUser.setAblaufdatum(dateToLocalDate(rs.getDate("Datum")));
        premiumUser.setBenutzername(rs.getString("Benutzername"));
        premiumUser.setPasswort(rs.getString("Passwort"));
        premiumUser.setEmail(rs.getString("Email"));
        return premiumUser;
    }

    private LocalDate dateToLocalDate(Date dateToConvert){
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
