package com.usermanagement.facade.impl;

import com.usermanagement.dto.UserDto;
import com.usermanagement.facade.UserFacade;
import com.usermanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;

    @Override
    public UserDto login(String username, String password) {
        UserDto foundUser = userService.getUserByUserName(username);
        if (foundUser == null) {
            System.out.println("User not found!");
            return null;
        }
        if (!foundUser.getPassword().equals(password)) {
            System.out.println("Incorrect password!");
            return null;
        }
        return foundUser;
    }

    @Override
    public boolean register(String name, String username, String password) {
        if (!username.contains("@")) {
            System.out.println( "Missing @ in username!");
            return false;
        }
        if (password.length() <= 4) {
            System.out.println("You must entered the password! Also the password must have at least 5 chars!");
            return false;
        }

        UserDto user = new UserDto();
        user.setName(name);
        user.setUsername(username);
        user.setPassword(password);

        return userService.addUser(user);
    }

    @Override
    public boolean delete(String username, String password, String usernameToBeDeleted) {
        String basicPrefix = "Basic ";
        String credentials = basicPrefix + username + ":" +password;
        boolean deleted = userService.deleteUser(username, credentials, usernameToBeDeleted);
        if (deleted) {
            return true;
        }
        return false;
    }

    @Override
    public boolean changePassword(String username, String passwordToChange) {
        if (passwordToChange.length() <= 4) {
            System.out.println("You must entered the password! Also the password must have at least 5 chars!");
            return false;
        }
        int modifiedRecords = userService.updatePassword(username, passwordToChange);
        return modifiedRecords != 0;
    }
}
