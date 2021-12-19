package de.hhu.propra.exercise4.service;

import de.hhu.propra.exercise4.model.entity.User;
import de.hhu.propra.exercise4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class PrinciaplService {
    @Autowired
    UserRepository userRepository;

    public User loadUserByPrincipal(Principal principal) throws Exception{
        return userRepository.getSingleUserByName(principal.getName());
    }
}
