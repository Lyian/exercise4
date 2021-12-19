package de.hhu.propra.exercise4.configuration.authentication;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication)   {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();


        ArrayList<GrantedAuthority> userRole = new ArrayList<>();
        if(name.equals("ann"))
            userRole.add(new SimpleGrantedAuthority("ROLE_USER"));
        else
            userRole.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return new UsernamePasswordAuthenticationToken(name, password, userRole);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}