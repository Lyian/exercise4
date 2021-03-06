package de.hhu.propra.exercise4.repository;

import de.hhu.propra.exercise4.model.entity.User;
import de.hhu.propra.exercise4.model.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import de.hhu.propra.exercise4.repository.helpers.QueryHelper;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<User> getAllUsers(){
        return jdbcTemplate.query("SELECT ROWID, * FROM Nutzer", new UserMapper());
    }

    @Nullable
    public List<User> getUserByMail(String mail) throws Exception {
        return jdbcTemplate.query("SELECT ROWID, * FROM Nutzer WHERE Email LIKE ?", new UserMapper(), QueryHelper.createLikeParam(mail));
    }

    @Nullable
    public User getSingleUserByMail(String mail) throws Exception {
        return jdbcTemplate.queryForObject("SELECT ROWID, * FROM Nutzer WHERE Email LIKE ?", new UserMapper(), mail);
    }

    public User getSingleUserByName(String name) throws Exception {
        return jdbcTemplate.queryForObject(
                "SELECT ROWID, * FROM Nutzer WHERE Benutzername = ?", new UserMapper(), name);
    }

    public Integer createNewUser(User user) throws Exception {
        return jdbcTemplate.update("INSERT INTO Nutzer (Email, Benutzername, Passwort, AdresseID) VALUES(?,?,?,?)", user.getEmail(), user.getBenutzername(), user.getPassword(), 1);
    }
}
