package de.hhu.propra.exercise4.repository;

import de.hhu.propra.exercise4.model.mapper.GenreMapper;
import de.hhu.propra.exercise4.model.entity.Genre;
import de.hhu.propra.exercise4.model.mapper.UserMapper;
import de.hhu.propra.exercise4.repository.helpers.QueryHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GenreRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Genre> getAllGenres() { return jdbcTemplate.query("SELECT ROWID, * FROM Genretabelle", new GenreMapper()); }

    @Nullable
    public List<Genre> getGenreByDesignation(String designation) throws Exception {
        return jdbcTemplate.query("SELECT ROWID, * FROM Genretabelle WHERE Genre LIKE ?", new GenreMapper(), QueryHelper.createLikeParam(designation));
    }

}
