package de.hhu.propra.exercise4.repository;

import de.hhu.propra.exercise4.model.entity.Album;
import de.hhu.propra.exercise4.model.entity.Title;
import de.hhu.propra.exercise4.model.entity.User;
import de.hhu.propra.exercise4.model.mapper.AlbumMapper;
import de.hhu.propra.exercise4.model.mapper.TitleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TitleRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Title> getAllTitles() {
        return jdbcTemplate.query("Select * from Titel", new TitleMapper());
    }

    public Integer createNewTitle(Title title) throws Exception {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Titel (Name, Dauer, PfadLQ, PfadHQ, Genre) VALUES(?,?,?,?,?)");
            ps.setString(1, title.getBezeichnung());
            ps.setInt(2, title.getDauer());
            ps.setString(3, title.getSpeicherort_lq());
            ps.setString(4, title.getSpeicherort_hq());
            ps.setString(5, "DumbRap");
            return ps;
        }, keyHolder);

        return (int) keyHolder.getKey();
    }

    public Integer insertNewTitleForArtist(User user, Title title) throws SQLException{
        return jdbcTemplate.update("INSERT INTO Kuenstler_macht_titel (Email, TitelID) VALUES (?,?)", user.getEmail(), title.getTitleid());
    }
}
