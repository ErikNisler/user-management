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

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final Map<String, String> validUsers = Map.of(
            "erik.nisler@seznam.cz", "password"
    );

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll().stream().map(userMapper::toDto).toList();
    }

    @Override
    public UserDto getUserById(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        return userEntity.map(userMapper::toDto).orElse(null);
    }

    @Override
    public UserDto getUserByUserName(String username) {
        Optional<UserEntity> userEntity = userRepository.findByUserName(username);
        return userEntity.map(userMapper::toDto).orElse(null);
    }

    @Transactional
    @Override
    public boolean addUser(UserDto userDto) {
        UserEntity savedUser = userRepository.save(userMapper.toEntity(userDto));
        return savedUser == null ? false : true;
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
    public boolean deleteUser(Long id, String credentials) {
        if (!isAuthorized(credentials)) {
            log.error("Wrong credentials!");
            return false;
        }
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);
        if (userEntityOptional.isPresent()) {
            userRepository.delete(userEntityOptional.get());
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public boolean deleteUser(String username, String credentials, String usernameToBeDeleted) {
        if (!isAuthorized(credentials)) {
            log.error("Wrong credentials!");
            return false;
        }
        Optional<UserEntity> userEntityOptional = userRepository.findByUserName(usernameToBeDeleted);
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

    private boolean isAuthorized(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Basic ")) {
            return false;
        }

        String base64Credentials = authorizationHeader.substring("Basic ".length());
        String credentials = Base64.getEncoder().encodeToString(base64Credentials.getBytes());

        String[] userAndPassword = base64Credentials.split(":", 2);
        if (userAndPassword.length != 2) {
            return false;
        }

        String username = userAndPassword[0];
        String password = userAndPassword[1];

        return validUsers.containsKey(username) && validUsers.get(username).equals(password);
    }
}
