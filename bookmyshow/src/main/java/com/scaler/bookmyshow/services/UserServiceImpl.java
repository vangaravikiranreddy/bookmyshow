package com.scaler.bookmyshow.services;

import com.scaler.bookmyshow.models.User;
import com.scaler.bookmyshow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new RuntimeException();
        }

        User user = userOptional.get();

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return user;
        }

        throw new RuntimeException();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public long signUp(String name, String email, String password) {

        User user = new User();
        user.setName(name);
        user.setEmail(email);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setBookings(new ArrayList<>());

        User savedUser = userRepository.save(user);

        return savedUser.getId();
    }

}
