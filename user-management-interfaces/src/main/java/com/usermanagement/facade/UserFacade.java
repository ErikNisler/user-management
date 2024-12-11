package com.usermanagement.facade;

import com.usermanagement.dto.UserDto;
import com.usermanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;

    public boolean login(UserDto userDto) {
        UserDto foundUser = userService.getUserByUserName(userDto.getUsername());
        if (foundUser == null) {
            return false;
        }
        return foundUser.getPassword().equals(userDto.getPassword());
    }

    public boolean register(UserDto userDto) {
        return userService.addUser(userDto);
    }

    public boolean delete(UserDto userDto) {
        return userService.addUser(userDto);
    }
}
