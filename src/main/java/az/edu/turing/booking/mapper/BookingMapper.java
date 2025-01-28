package az.edu.turing.booking.mapper;

import az.edu.turing.booking.domain.entity.BookingEntity;
import az.edu.turing.booking.domain.entity.FlightEntity;
import az.edu.turing.booking.model.dto.response.ResponseBookingDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Duration;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "flight.airlineName", target = "airlineName")
    @Mapping(source = "flight.departureCity", target = "departureCity")
    @Mapping(source = "flight.arrivalCity", target = "arrivalCity")
    @Mapping(source = "flight.departureAirport", target = "departureAirport")
    @Mapping(source = "flight.arrivalAirport", target = "arrivalAirport")
    @Mapping(source = "flight.departureTime", target = "departureTime")
    @Mapping(source = "flight.arrivalTime", target = "arrivalTime")
    @Mapping(source = "flight", target = "duration", qualifiedByName = "formatDuration")
    @Mapping(source = "seatNumber", target = "seatNumber")
    @Mapping(source = "classType", target = "classType")
    @Mapping(source = "status", target = "status")
    ResponseBookingDto toResponseBookingDto(BookingEntity bookingEntity);

    @Named("formatDuration")
    default String formatDuration(FlightEntity flightEntity) {
        Duration duration = flightEntity.getDuration();
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        return String.format("%02d:%02d", hours, minutes);
    }

    List<ResponseBookingDto> toResponseBookingDtoList(List<BookingEntity> bookingEntities);
}
