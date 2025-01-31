package az.edu.turing.booking.domain.repository;

import az.edu.turing.booking.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query(nativeQuery = true, value = "SELECT COUNT(u) > 0 FROM users u WHERE u.id = :userId AND u.role = 'ADMIN'")
    boolean existsByIdAndRoleAdmin(Long userId);
}
