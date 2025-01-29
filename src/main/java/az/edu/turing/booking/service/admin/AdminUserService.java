package az.edu.turing.booking.service.admin;

import az.edu.turing.booking.domain.entity.UserEntity;
import az.edu.turing.booking.domain.repository.UserRepository;
import az.edu.turing.booking.exception.NotFoundException;
import az.edu.turing.booking.mapper.UserMapper;
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

    private final UserRepository repository;
    private final UserMapper userMapper;

    @Transactional
    public void deleteById(long id) {
        UserEntity userById = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with specified id not found"));
        userById.setStatus(UserStatus.DELETED);
        repository.save(userById);
    }

    public List<UserDto> getAll() {
        return repository.findAll().stream().map(userMapper::toUserDto).toList();
    }

    public UserDto getById(long id) {
        UserEntity userEntity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with specified id not found"));
        return userMapper.toUserDto(userEntity);
    }
}
