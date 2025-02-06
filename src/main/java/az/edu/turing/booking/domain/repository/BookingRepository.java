package az.edu.turing.booking.domain.repository;

import az.edu.turing.booking.domain.entity.BookingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {

    @EntityGraph(attributePaths = {"passenger", "flight"})
    List<BookingEntity> findAll();

    Page<BookingEntity> findAllByPassengerId(Long passengerId, Pageable pageable);
}
