package az.edu.turing.booking.service.admin;

import az.edu.turing.booking.domain.entity.UserEntity;
import az.edu.turing.booking.domain.repository.UserRepository;
import az.edu.turing.booking.exception.NotFoundException;
import az.edu.turing.booking.exception.UnauthorizedAccessException;
import az.edu.turing.booking.mapper.UserMapper;
import az.edu.turing.booking.model.dto.request.UpdateUserDto;
import az.edu.turing.booking.model.dto.response.UserDto;
import az.edu.turing.booking.model.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public void deleteById(Long userId, Long adminId) {
        checkAdminExistence(adminId);
        UserEntity userById = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with specified id not found"));
        userById.setStatus(UserStatus.DELETED);
        userRepository.save(userById);
    }

    public List<UserDto> getAll(Long adminId) {
        checkAdminExistence(adminId);
        return userRepository.findAll().stream().map(userMapper::toUserDto).toList();
    }

    public UserDto getById(Long userId, Long adminId) {
        checkAdminExistence(adminId);
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with specified id not found"));
        return userMapper.toUserDto(userEntity);
    }

    @Transactional
    public UserDto update(Long id, Long adminId, UpdateUserDto updatedUserDto) {
        checkAdminExistence(adminId);

        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with specified id not found"));

        UserEntity updatedUserEntity = userMapper.updateUserEntityFromDto(user, updatedUserDto);
        updatedUserEntity.setUpdatedBy(adminId);

        return userMapper.toUserDto(userRepository.save(updatedUserEntity));
    }

    private void checkAdminExistence(Long adminId) {
        if (!userRepository.existsByIdAndRoleAdmin(adminId)) {
            throw new UnauthorizedAccessException("Admin with specified admin id not found");
        }
    }
}
