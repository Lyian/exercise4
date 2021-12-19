package de.hhu.propra.exercise4.repository;

import de.hhu.propra.exercise4.model.entity.Album;
import de.hhu.propra.exercise4.model.entity.User;
import de.hhu.propra.exercise4.model.mapper.AlbumMapper;
import de.hhu.propra.exercise4.model.mapper.UserMapper;
import de.hhu.propra.exercise4.repository.helpers.QueryHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AlbumRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Album> getAllAlbenWithTracks(){
        return jdbcTemplate.query("Select ID, Name, Jahr, Count(ttva.AlbumID) as Tracks from Album join titel_teil_von_album ttva on Album.ID = ttva.AlbumID group by AlbumID", new AlbumMapper());
    }

    @Nullable
    public List<User> getUserByMail(String mail) throws Exception {
        return jdbcTemplate.query("SELECT ROWID, * FROM Nutzer WHERE Email LIKE ?", new UserMapper(), QueryHelper.createLikeParam(mail));
    }

    @Nullable
    public User getSingleUserByMail(String mail) throws Exception {
        return jdbcTemplate.queryForObject("SELECT ROWID, * FROM Nutzer WHERE Email LIKE ?", new UserMapper(), mail);
    }

    public Integer createNewUser(User user) throws Exception {
        return jdbcTemplate.update("INSERT INTO Nutzer (Email, Benutzername, Passwort, AdresseID) VALUES(?,?,?,?)", user.getEmail(), user.getBenutzername(), user.getPassword(), 1);
    }
}
