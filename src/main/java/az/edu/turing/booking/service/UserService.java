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
        UserEntity userEntity = new UserEntity();

        userEntity.setFirstName(request.firstName());
        userEntity.setLastName(request.lastName());
        userEntity.setEmail(request.email());
        userEntity.setPhoneNumber(request.phoneNumber());
        userEntity.setDateOfBirth(request.dateOfBirth());
        userEntity.setNationality(request.nationality());
        userEntity.setRole(Role.USER);
        userEntity.setStatus(UserStatus.ACTIVE);

        UserEntity savedUser = userRepository.save(userEntity);

        return userMapper.toUserDto(savedUser);
    }
}
