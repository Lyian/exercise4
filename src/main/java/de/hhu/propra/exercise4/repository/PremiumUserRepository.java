package de.hhu.propra.exercise4.repository;

import de.hhu.propra.exercise4.model.entity.PremiumUser;
import de.hhu.propra.exercise4.model.entity.User;
import de.hhu.propra.exercise4.model.mapper.PremiumUserMapper;
import de.hhu.propra.exercise4.model.mapper.UserMapper;
import de.hhu.propra.exercise4.repository.helpers.QueryHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PremiumUserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<PremiumUser> getAllUsers() {
        return jdbcTemplate.query("Select P.ROWID as PremiumnutzerId, Nutzer.ROWID as NutzerId, Nutzer.Email, Nutzer.Benutzername, Nutzer.Passwort, Datum from Nutzer join Premiumnutzer P on Nutzer.Email = P.Email", new PremiumUserMapper());
    }

    @Nullable
    public List<PremiumUser> getUserByMail(String mail) throws Exception {
        return jdbcTemplate.query("Select P.ROWID as PremiumnutzerId, Nutzer.ROWID as NutzerId, Nutzer.Email, Nutzer.Benutzername, Nutzer.Passwort, Datum from Nutzer join Premiumnutzer P on Nutzer.Email = P.Email WHERE P.Email like ?", new PremiumUserMapper(), QueryHelper.createLikeParam(mail));
    }


    public Integer createNewUser(PremiumUser premiumUser) throws Exception {
        String dateToSave = premiumUser.getAblaufdatum() + " 00:00:00";
        return jdbcTemplate.update("INSERT INTO Premiumnutzer (Email, Datum) VALUES(?,?)", premiumUser.getEmail(), dateToSave);
    }
}
