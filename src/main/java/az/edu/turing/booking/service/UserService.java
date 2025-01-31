package az.edu.turing.booking.service;


import az.edu.turing.booking.domain.entity.UserEntity;
import az.edu.turing.booking.domain.repository.UserRepository;
import az.edu.turing.booking.exception.NotFoundException;
import az.edu.turing.booking.mapper.UserMapper;
import az.edu.turing.booking.model.dto.request.CreateUserRequest;
import az.edu.turing.booking.model.dto.response.UserDto;
import az.edu.turing.booking.model.enums.Role;
import az.edu.turing.booking.model.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public void deleteById(long id) {
        UserEntity userById = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with specified id not found"));
        userById.setStatus(UserStatus.DELETED);
        userRepository.save(userById);
    }

    @Transactional
    public UserDto create(CreateUserRequest request) {
        UserEntity savedUser = userRepository.save(userMapper.toUserEntity(request));

        return userMapper.toUserDto(savedUser);
    }
}
