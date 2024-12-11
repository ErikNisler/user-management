package com.usermanagement.service;

import com.usermanagement.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAll();

    UserDto getUserById(Long id);

    void addUser(UserDto userDto);

    void updateUser(Long id, String name);

    boolean deleteUser(Long id);
}
