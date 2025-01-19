import com.usermanagement.dto.UserDto;
import com.usermanagement.entity.UserEntity;
import com.usermanagement.repository.UserRepository;
import com.usermanagement.service.impl.UserServiceImpl;
import com.usermanagement.service.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    UserDto userDto;

    UserEntity userEntity;

    @BeforeEach
    void setup() {;
        userDto = new UserDto();
        userDto.setUsername("testuser");
        userDto.setName("Test Name");
        userDto.setPassword("password123");

        userEntity = new UserEntity();
        userEntity.setUsername("testuser");
        userEntity.setName("Test Name");
        userEntity.setPassword("password123");

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addUser_Successfully() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        when(userMapper.toEntity(userDto)).thenReturn(userEntity);

        boolean added = userService.addUser(userDto);

        assertTrue(added);
        verify(userRepository, times(1)).save(userEntity);
        verify(userMapper, times(1)).toEntity(userDto);
    }

    @Test
    void addUser_ThrowsException() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(userEntity));

        Exception e = assertThrows(IllegalArgumentException.class,() -> userService.addUser(userDto));

        assertEquals("Username already exists", e.getMessage());
        assertInstanceOf(IllegalArgumentException.class, e);
        verify(userRepository, never()).save(userEntity);
        verify(userMapper, never()).toEntity(userDto);
    }

    @Test
    void getUserById_Successfully() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userEntity));
        when(userMapper.toDto(userEntity)).thenReturn(userDto);

        UserDto user = userService.getUserById(1L);

        assertNotNull(user);
    }

    @Test
    void getUserById_UserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(userMapper.toDto(any())).thenReturn(null);

        UserDto user = userService.getUserById(1L);

        assertNull(user);
    }

    @Test
    void deleteUser_Successfully() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userEntity));

        boolean deleted = userService.deleteUser(1L);

        assertTrue(deleted);
        assertNotNull(userRepository.findById(1L));
        verify(userRepository, times(1)).delete(userEntity);
    }

    @Test
    void deleteUser_UserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        boolean deleted = userService.deleteUser(1L);

        assertFalse(deleted);
        assertEquals(Optional.empty(), userRepository.findById(1L));
        verify(userRepository, never()).delete(userEntity);
    }

    @Test
    void updateUser_Successfully() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userEntity));
        when(userMapper.toDto(any())).thenReturn(userDto);

        UserDto updated = userService.updateUser(1L, userDto);

        assertNotNull(updated);
        assertEquals(Optional.of(userEntity), userRepository.findById(1L));
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    void updateUser_UserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        UserDto updated = userService.updateUser(1L, userDto);

        assertNull(updated);
        assertEquals(Optional.empty(), userRepository.findById(1L));
        verify(userRepository, never()).save(userEntity);
    }
}
