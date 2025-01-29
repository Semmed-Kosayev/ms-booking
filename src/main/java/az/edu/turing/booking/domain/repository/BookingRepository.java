package az.edu.turing.booking.domain.repository;

import az.edu.turing.booking.domain.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
}
