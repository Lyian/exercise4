package de.hhu.propra.exercise4.service;

import de.hhu.propra.exercise4.model.entity.PremiumUser;
import de.hhu.propra.exercise4.model.entity.User;
import de.hhu.propra.exercise4.repository.PremiumUserRepository;
import de.hhu.propra.exercise4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PremiumUserService {
    @Autowired
    PremiumUserRepository premiumUserRepository;

    @Autowired
    UserRepository userRepository;

    public List<PremiumUser> getAllPremiumUsersByValidity(String abgelaufen){
        List<PremiumUser> premiumUsers = premiumUserRepository.getAllUsers();
        LocalDate date = LocalDate.now();

        if(abgelaufen.equals("true")){
            return premiumUsers.stream()
                    .filter(premiumUser -> date.isAfter(premiumUser.getAblaufdatum()))
                    .collect(Collectors.toList());
        }else{
            return premiumUsers.stream()
                    .filter(premiumUser -> date.isBefore(premiumUser.getAblaufdatum()))
                    .collect(Collectors.toList());
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    public void createNewPremiumUser(PremiumUser premiumUser) throws Exception {
        try {
            User user = userRepository.getSingleUserByMail(premiumUser.getEmail());
            premiumUserRepository.createNewUser(premiumUser);
        }catch (Exception e){
            try{
                User user = new User(0, premiumUser.getEmail(), premiumUser.getPasswort(), premiumUser.getBenutzername());
                userRepository.createNewUser(user);
                premiumUserRepository.createNewUser(premiumUser);
            }
            catch (Exception ex){
                throw ex;
            }
        }
    }
}
