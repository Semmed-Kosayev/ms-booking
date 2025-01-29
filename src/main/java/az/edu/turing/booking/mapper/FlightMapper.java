package az.edu.turing.booking.mapper;

import az.edu.turing.booking.domain.entity.FlightEntity;
import az.edu.turing.booking.model.dto.FlightDto;
import az.edu.turing.booking.model.dto.response.UpdateFlightResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FlightMapper {
    @Mapping(source = "flightDetails.aircraftModel", target = "aircraftModel")
    @Mapping(source = "flightDetails.departureTerminal", target = "departureTerminal")
    @Mapping(source = "flightDetails.arrivalTerminal", target = "arrivalTerminal")
    @Mapping(source = "flightDetails.gateNumber", target = "gateNumber")
    @Mapping(source = "flightDetails.maxBaggageWeight", target = "maxBaggageWeight")
    @Mapping(source = "flightDetails.isWifiAvailable", target = "isWifiAvailable")
    @Mapping(source = "flightDetails.availableSeats", target = "availableSeats")
    @Mapping(source = "flightDetails.maxSeats", target = "maxSeats")
    @Mapping(source = "flightDetails.status", target = "flightStatus")
    UpdateFlightResponse toFlightResponseDto(FlightEntity flightEntity);

    FlightMapper INSTANCE = Mappers.getMapper(FlightMapper.class);

    @Mappings({
            @Mapping(source = "flightDetails.aircraftModel", target = "aircraftModel"),
            @Mapping(source = "flightDetails.departureTerminal", target = "departureTerminal"),
            @Mapping(source = "flightDetails.arrivalTerminal", target = "arrivalTerminal"),
            @Mapping(source = "flightDetails.gateNumber", target = "gateNumber"),
            @Mapping(source = "flightDetails.maxBaggageWeight", target = "maxBaggageWeight"),
            @Mapping(source = "flightDetails.isWifiAvailable", target = "isWifiAvailable"),
            @Mapping(source = "flightDetails.availableSeats", target = "availableSeats"),
            @Mapping(source = "flightDetails.maxSeats", target = "maxSeats"),
            @Mapping(source = "flightDetails.status", target = "flightStatus"),
            @Mapping(source = "departureCity", target = "departureCity"),
            @Mapping(source = "arrivalCity", target = "arrivalCity")
    })
    FlightDto toFlightDto(FlightEntity flightEntity);
}
