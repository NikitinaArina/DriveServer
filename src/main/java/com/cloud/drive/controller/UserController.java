package com.cloud.drive.controller;

import com.cloud.drive.entity.User;
import com.cloud.drive.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.security.auth.login.AccountNotFoundException;
import java.util.Arrays;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public User createUser(@RequestBody User user) throws AuthenticationException {
        String encodedPassword = userService.encodePassword(Arrays.toString(user.getPassword()));

        User userDB = new User(user.getUsername(), encodedPassword.toCharArray());

        return userService.createUser(userDB);
    }

    @PutMapping("/update")
    public User updateUser(@RequestBody User user) throws AccountNotFoundException {
        String encodedPassword = userService.encodePassword(Arrays.toString(user.getPassword()));
        user.setPassword(encodedPassword.toCharArray());
        return userService.updateUser(user);
    }

    @PostMapping("/login")
    public User loginUser(@RequestBody User user) {
        return userService.getUser(user);
    }
}
