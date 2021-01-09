package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    private AuthenticationService authenticationService;
    private HashService hashService;
    private UserMapper userMapper;

    public UserService(AuthenticationService authenticationService, HashService hashService, UserMapper userMapper) {
        this.authenticationService = authenticationService;
        this.hashService = hashService;
        this.userMapper = userMapper;
    }



    public void login(User user){

    }

    public User getUserByUsername(String username) {
        return userMapper.getUser(username);
    }

    public boolean isExistingUser(String username) {
        return getUserByUsername(username) != null;
    }

    public Integer createUser(User user){
        if (isExistingUser(user.getUsername()))
            return -1;
        SecureRandom secureRandom = new SecureRandom();
        byte [] salt = new byte[16];
        secureRandom.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        return userMapper.saveUser(new User(null, user.getUsername(),hashedPassword, encodedSalt, user.getFirstname(), user.getLastname()));
    }
}
