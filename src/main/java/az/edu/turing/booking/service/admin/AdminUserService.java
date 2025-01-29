package az.edu.turing.booking.service.admin;

import az.edu.turing.booking.domain.entity.UserEntity;
import az.edu.turing.booking.domain.repository.UserRepository;
import az.edu.turing.booking.exception.NotFoundException;
import az.edu.turing.booking.model.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminUserService {
    private final UserRepository repository;

    @Transactional
    public void deleteById(long id) {
        UserEntity userById = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with specified id not found"));
        userById.setStatus(UserStatus.DELETED);
        repository.save(userById);
    }
}
