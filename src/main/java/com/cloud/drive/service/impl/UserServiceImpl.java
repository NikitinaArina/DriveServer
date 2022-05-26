package com.cloud.drive.service.impl;

import com.cloud.drive.entity.Image;
import com.cloud.drive.entity.User;
import com.cloud.drive.exception.NoSuchEntityException;
import com.cloud.drive.repository.ImageStorageRepository;
import com.cloud.drive.repository.UserRepository;
import com.cloud.drive.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import javax.security.auth.login.AccountNotFoundException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserService {
    private final static Logger log = Logger.getLogger(UserServiceImpl.class.getName());
    private final UserRepository userRepository;
    private final ImageStorageRepository imageStorageRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ImageStorageRepository imageStorageRepository) {
        this.userRepository = userRepository;
        this.imageStorageRepository = imageStorageRepository;
    }

    @Override
    public User createUser(User user) throws AuthenticationException {
        boolean isUserExist = isUserExist(user);

        if (isUserExist) {
            throw new AuthenticationException("User is already exist");
        } else {
            log.info("User with username: " + user.getUsername() + " is created");
            return userRepository.save(new User(user.getUsername(), user.getPassword()));
        }
    }

    @Override
    public User updateUser(User user) throws AccountNotFoundException {
        boolean isUserExist = isUserExist(user);

        if (isUserExist) {
            Image image = imageStorageRepository.findImageByUserId(user.getId());
            user.setImage(image);

            return userRepository.save(user);
        } else {
            throw new AccountNotFoundException();
        }
    }

    @Override
    public User getUser(User user) {
        User userFromDB = userRepository.findUserByUsername(user.getUsername());

        if (userFromDB == null) {
            throw new NoSuchEntityException("There is no user with username — " + user.getUsername());
        }

        if (Arrays.equals(userFromDB.getPassword(), encodePassword(Arrays.toString(user.getPassword())).toCharArray())) {
            return userFromDB;
        }
        throw new NoSuchEntityException("Username or password");
    }

    @Override
    public String encodePassword(String password) {
        try {
            MessageDigest msd = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = msd.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder encryptionValue = new StringBuilder(2 * encodedHash.length);

            for (byte hash : encodedHash) {
                String hexVal = Integer.toHexString(0xff & hash);
                if (hexVal.length() == 1) {
                    encryptionValue.append('0');
                }
                encryptionValue.append(hexVal);
            }

            return encryptionValue.toString();
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    private boolean isUserExist(User user) {
        return userRepository.findUserByUsername(user.getUsername()) != null;
    }
}
