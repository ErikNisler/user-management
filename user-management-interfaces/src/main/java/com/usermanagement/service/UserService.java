package com.usermanagement.service;

import com.usermanagement.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAll();

    UserDto getUserById(Long id);

    UserDto getUserByUserName(String username);

    boolean addUser(UserDto userDto);

    UserDto updateUser(Long id, UserDto userDto);

    boolean deleteUser(Long id, String credentials);

    boolean deleteUser(String username, String password, String usernameToBeDeleted);

    int updatePassword(String username, String passwordToBeChanged);
}
