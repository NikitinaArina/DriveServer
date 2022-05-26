package com.cloud.drive.service.interfaces;


import com.cloud.drive.entity.User;

import javax.naming.AuthenticationException;
import javax.security.auth.login.AccountNotFoundException;

public interface UserService {
    User createUser(User user) throws AuthenticationException;

    User updateUser(User user) throws AccountNotFoundException;

    User getUser(User user);

    String encodePassword(String password);
}
