package de.hhu.propra.exercise4.configuration.authentication;

import de.hhu.propra.exercise4.model.entity.Artist;
import de.hhu.propra.exercise4.model.entity.PremiumUser;
import de.hhu.propra.exercise4.model.entity.User;
import de.hhu.propra.exercise4.repository.ArtistRepository;
import de.hhu.propra.exercise4.repository.PremiumUserRepository;
import de.hhu.propra.exercise4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PremiumUserRepository premiumUserRepository;

    @Autowired
    ArtistRepository artistRepository;


    @Override
    public Authentication authenticate(Authentication authentication) {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        ArrayList<GrantedAuthority> userRoles = new ArrayList<>();

        try{
            User user = userRepository.getSingleUserByName(name);

            if(!user.getPassword().equals(password)){
                throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
            }

            List<PremiumUser> premiumUsers = premiumUserRepository.getUserByMail(user.getEmail());
            List<Artist> artists = artistRepository.getArtistByMail(user.getEmail());

            userRoles.add(new SimpleGrantedAuthority("ROLE_USER"));

            if(premiumUsers.size() > 0){
                userRoles.add(new SimpleGrantedAuthority("ROLE_PREMIUM_USER"));
            }

            if(artists.size() > 0){
                userRoles.add(new SimpleGrantedAuthority("ROLE_ARTIST"));
            }
        }catch (Exception e){
            throw new AuthenticationServiceException("Did not work");
        }

        return new UsernamePasswordAuthenticationToken(name, password, userRoles);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}