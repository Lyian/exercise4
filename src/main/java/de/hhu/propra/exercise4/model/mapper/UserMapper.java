package de.hhu.propra.exercise4.model.mapper;

import de.hhu.propra.exercise4.model.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setNutzerId(rs.getInt("ROWID"));
        user.setBenutzername(rs.getString("Benutzername"));
        user.setPassword(rs.getString("Passwort"));
        user.setEmail(rs.getString("Email"));
        return user;
    }
}
