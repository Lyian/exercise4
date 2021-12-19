package de.hhu.propra.exercise4.repository;

import de.hhu.propra.exercise4.model.entity.Artist;
import de.hhu.propra.exercise4.model.entity.User;
import de.hhu.propra.exercise4.model.mapper.ArtistMapper;
import de.hhu.propra.exercise4.model.mapper.PremiumUserMapper;
import de.hhu.propra.exercise4.model.mapper.UserMapper;
import de.hhu.propra.exercise4.repository.helpers.QueryHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArtistRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Artist> getAllArtists(){
        return jdbcTemplate.query("Select P.ROWID as Premiumnutzerid, N.ROWID as Nutzerid,  Name, N.Email, N.Benutzername, N.Passwort, P.Datum from Kuenstler join Premiumnutzer P on P.Email = Kuenstler.Email JOIN Nutzer N on N.Email = P.Email", new ArtistMapper());
    }

    @Nullable
    public List<Artist> getArtistByMail(String mail) throws Exception{
        return jdbcTemplate.query("Select P.ROWID as Premiumnutzerid, N.ROWID as Nutzerid,  Name, N.Email, N.Benutzername, N.Passwort, P.Datum from Kuenstler join Premiumnutzer P on P.Email = Kuenstler.Email JOIN Nutzer N on N.Email = P.Email WHERE P.Email like ?", new ArtistMapper(), QueryHelper.createLikeParam(mail));
    }


    public Integer createNewArtist(Artist artist) throws Exception {
        return jdbcTemplate.update("INSERT INTO Kuenstler (Email, Name) VALUES(?,?)", artist.getEmail(), artist.getKuenstlername());
    }
}
