package com.usermanagement.service;

import com.usermanagement.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAll();

    UserDto getUserById(Long id);

    UserDto addUser(UserDto userDto);

    UserDto updateUser(Long id, UserDto userDto);

    boolean deleteUser(Long id);
}
