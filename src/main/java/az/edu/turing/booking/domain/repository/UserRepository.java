package az.edu.turing.booking.domain.repository;

import az.edu.turing.booking.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
