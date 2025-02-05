package com.usermanagement.service.impl;

import com.usermanagement.dto.UserDto;
import com.usermanagement.entity.UserEntity;
import com.usermanagement.repository.UserRepository;
import com.usermanagement.service.UserService;
import com.usermanagement.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto getUserById(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        return userEntity.map(userMapper::toDto).orElse(null);
    }

    @Transactional
    @Override
    public boolean addUser(UserDto userDto) {
        Optional<UserEntity> existingUser = userRepository.findByUsername(userDto.getUsername());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        userRepository.save(userMapper.toEntity(userDto));
        return true;
    }

    @Transactional
    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);
        if (userEntityOptional.isPresent()) {
            UserEntity updatedUser = userRepository.save(userMapper.toEntity(userDto));
            return userMapper.toDto(updatedUser);
        }
        return null;
    }

    @Transactional
    @Override
    public boolean deleteUser(Long id) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);
        if (userEntityOptional.isPresent()) {
            userRepository.delete(userEntityOptional.get());
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public int updatePassword(String username, String passwordToBeChanged) {
        return userRepository.updatePassword(username,passwordToBeChanged);
    }

}
