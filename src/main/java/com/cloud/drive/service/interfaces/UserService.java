package com.cloud.drive.service.interfaces;


import com.cloud.drive.entity.User;

import javax.naming.AuthenticationException;

public interface UserService {
    User createUser(User user) throws AuthenticationException;

    User getUser(User user);

    String encodePassword(String password);
}
