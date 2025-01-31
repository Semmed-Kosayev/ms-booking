package az.edu.turing.booking.mapper;

import az.edu.turing.booking.domain.entity.BookingEntity;
import az.edu.turing.booking.domain.entity.FlightEntity;
import az.edu.turing.booking.domain.entity.UserEntity;
import az.edu.turing.booking.model.dto.request.BookingUpdateRequest;
import az.edu.turing.booking.model.dto.request.CreateBookingRequest;
import az.edu.turing.booking.model.dto.response.BookingDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
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
    BookingDto toBookingDto(BookingEntity bookingEntity);

    @Named("formatDuration")
    default String formatDuration(FlightEntity flightEntity) {
        Duration duration = flightEntity.getDuration();
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        return String.format("%02d:%02d", hours, minutes);
    }

    List<BookingDto> toResponseBookingDtoList(List<BookingEntity> bookingEntities);

    @Mapping(target = "seatNumber", source = "updateRequest.seatNumber")
    @Mapping(target = "classType", source = "updateRequest.classType")
    @Mapping(target = "status", source = "updateRequest.status")
    @Mapping(target = "passenger", source = "userEntity")
    @Mapping(target = "flight", source = "flightEntity")
    @Mapping(target = "flightDate", source = "flightEntity.departureTime")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedBy", source = "updateRequest.adminId")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    BookingEntity updateBookingEntityFromRequest(@MappingTarget BookingEntity bookingEntity,
                                                 BookingUpdateRequest updateRequest,
                                                 UserEntity userEntity,
                                                 FlightEntity flightEntity);

    @Mapping(target = "status", constant = "CONFIRMED")
    @Mapping(target = "flightDate", source = "flightEntity.departureTime")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "createdBy", source = "request.passengerId")
    @Mapping(target = "updatedBy", source = "request.passengerId")
    @Mapping(target = "passenger", source = "userEntity")
    @Mapping(target = "flight", source = "flightEntity")
    @Mapping(target = "id", ignore = true)
    BookingEntity toBookingEntity(CreateBookingRequest request,
                                  FlightEntity flightEntity,
                                  UserEntity userEntity);
}
