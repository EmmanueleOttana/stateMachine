package co.develhope.login_system.order.services;

import co.develhope.login_system.user.entities.User;
import co.develhope.login_system.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RiderService {

    @Autowired
    private UserRepository userRepository;

    public User pickRider() {
        Optional<User> rider = userRepository.pickRider();
        if(rider.isPresent())return rider.get();
        return userRepository.findAll(PageRequest.of(0,1)).toList().get(0);
    }
}