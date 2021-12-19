package de.hhu.propra.exercise4.service;

import de.hhu.propra.exercise4.model.entity.Artist;
import de.hhu.propra.exercise4.model.entity.PremiumUser;
import de.hhu.propra.exercise4.model.entity.User;
import de.hhu.propra.exercise4.repository.ArtistRepository;
import de.hhu.propra.exercise4.repository.PremiumUserRepository;
import de.hhu.propra.exercise4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtistService {
    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    PremiumUserRepository premiumUserRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional(rollbackFor = {Exception.class})
    public void createNewArtist(Artist artist) throws Exception {
        createUser(artist);
        createNewPremiumUser(artist);

        try {
            artistRepository.createNewArtist(artist);
        } catch (Exception e) {
            throw e;
        }
    }

    private void createUser(Artist artist) throws Exception {
        try {
            List<User> users = userRepository.getUserByMail(artist.getEmail());

            if (users.isEmpty()) {
                User user = new User(0, artist.getEmail(), artist.getPasswort(), artist.getBenutzername());
                userRepository.createNewUser(user);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void createNewPremiumUser(Artist artist) throws Exception{
        try {
            List<PremiumUser> premiumUsers = premiumUserRepository.getUserByMail(artist.getEmail());

            if (premiumUsers.isEmpty()) {
                PremiumUser premiumUser = new PremiumUser(
                        0,
                        0,
                        artist.getAblaufdatum(),
                        artist.getEmail(),
                        artist.getPasswort(),
                        artist.getBenutzername()
                );

                premiumUserRepository.createNewUser(premiumUser);
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
