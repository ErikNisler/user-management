package com.usermanagement.service;

import com.usermanagement.dto.UserDto;

import java.sql.SQLException;

public interface UserService {

    UserDto getUserById(Long id);

    boolean addUser(UserDto userDto);

    UserDto updateUser(Long id, UserDto userDto);

    boolean deleteUser(Long id);

    int updatePassword(String username, String passwordToBeChanged);
}
