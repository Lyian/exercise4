package de.hhu.propra.exercise4.model.mapper;

import de.hhu.propra.exercise4.model.entity.Genre;
import de.hhu.propra.exercise4.model.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreMapper implements RowMapper<Genre> {

    @Override
    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        Genre genre = new Genre();
        genre.setGenreid(rs.getInt("ROWID"));
        genre.setBezeichnung(rs.getString("Genre"));
        return genre;
    }
}
