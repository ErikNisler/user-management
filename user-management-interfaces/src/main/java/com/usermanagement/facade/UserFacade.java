package com.usermanagement.facade;

import com.usermanagement.dto.UserDto;

public interface UserFacade {

    UserDto login(String username, String password);

    boolean register(String name, String username, String password);

    boolean delete(String username, String password, String usernameToBeDeleted);

    boolean changePassword(String username, String passwordToChange);
}
