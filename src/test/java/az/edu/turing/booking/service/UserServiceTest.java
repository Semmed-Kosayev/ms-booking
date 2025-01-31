package az.edu.turing.booking.service;

import az.edu.turing.booking.domain.entity.UserEntity;
import az.edu.turing.booking.domain.repository.UserRepository;
import az.edu.turing.booking.exception.NotFoundException;
import az.edu.turing.booking.mapper.UserMapper;
import az.edu.turing.booking.model.dto.request.CreateUserRequest;
import az.edu.turing.booking.model.dto.response.UserDto;
import az.edu.turing.booking.model.enums.Nationality;
import az.edu.turing.booking.model.enums.Role;
import az.edu.turing.booking.model.enums.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private UserEntity userEntity;
    private UserDto userDto;
    private CreateUserRequest createUserRequest;

    @BeforeEach
    void setUp() {
        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setFirstName("John");
        userEntity.setLastName("Doe");
        userEntity.setEmail("john.doe@example.com");
        userEntity.setPhoneNumber("+1234567890");
        userEntity.setRole(Role.USER);
        userEntity.setStatus(UserStatus.ACTIVE);

        userDto = new UserDto(1L, "John", "Doe", "john.doe@example.com",
                "+1234567890", LocalDate.of(1990, 5, 20),
                Nationality.CANADA, Role.USER, UserStatus.ACTIVE);

        createUserRequest = new CreateUserRequest("John", "Doe", "john.doe@example.com",
                "+1234567890", LocalDate.of(1990, 5, 20),
                Nationality.CANADA);
    }

    @Test
    void deleteById_ShouldMarkUserAsDeleted_WhenUserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));

        userService.deleteById(1L);

        assertEquals(UserStatus.DELETED, userEntity.getStatus());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    void deleteById_ShouldThrowNotFoundException_WhenUserNotExists() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> userService.deleteById(2L));
        assertEquals("User with specified id not found", exception.getMessage());

        verify(userRepository, times(1)).findById(2L);
        verify(userRepository, never()).save(any());
    }

    @Test
    void create_ShouldSaveUserAndReturnUserDto() {
        when(userMapper.toUserEntity(any(CreateUserRequest.class))).thenReturn(userEntity);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(userMapper.toUserDto(any(UserEntity.class))).thenReturn(userDto);

        UserDto result = userService.create(createUserRequest);

        assertNotNull(result);
        assertEquals(userDto.id(), result.id());
        assertEquals(userDto.email(), result.email());

        verify(userMapper, times(1)).toUserEntity(createUserRequest);
        verify(userRepository, times(1)).save(userEntity);
        verify(userMapper, times(1)).toUserDto(userEntity);
    }
}
