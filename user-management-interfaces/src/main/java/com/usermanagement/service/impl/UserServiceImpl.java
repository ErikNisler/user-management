package com.usermanagement.service.impl;

import com.usermanagement.dto.UserDto;
import com.usermanagement.entity.UserEntity;
import com.usermanagement.repository.UserRepository;
import com.usermanagement.service.UserService;
import com.usermanagement.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll().stream().map(userMapper::toDto).toList();
    }

    @Override
    public UserDto getUserById(Long id) {
        return userMapper.toDto(userRepository.getUserById(id));
    }

    @Transactional
    @Override
    public void addUser(UserDto userDto) {
        addUser(userMapper.toEntity(userDto));
    }

    private void addUser(UserEntity userEntity) {
        userRepository.addUser(userEntity.getId(), userEntity.getName());
    }

    @Transactional
    @Override
    public void updateUser(Long id, String name) {
        userRepository.updateUser(id, name);
    }

    @Override
    public boolean deleteUser(Long id) {
        return false;
    }
}
