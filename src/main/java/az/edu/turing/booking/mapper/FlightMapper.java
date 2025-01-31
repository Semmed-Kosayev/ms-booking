package az.edu.turing.booking.mapper;

import az.edu.turing.booking.domain.entity.FlightDetailsEntity;
import az.edu.turing.booking.domain.entity.FlightEntity;
import az.edu.turing.booking.model.dto.request.UpdateFlightRequest;
import az.edu.turing.booking.model.dto.response.FlightDto;
import az.edu.turing.booking.model.dto.request.CreateFlightRequest;
import az.edu.turing.booking.model.dto.response.UpdateFlightResponse;
import az.edu.turing.booking.model.enums.FlightStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FlightMapper {

    FlightMapper INSTANCE = Mappers.getMapper(FlightMapper.class);

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

    @Mapping(source = "flightDetails.aircraftModel", target = "aircraftModel")
    @Mapping(source = "flightDetails.departureTerminal", target = "departureTerminal")
    @Mapping(source = "flightDetails.arrivalTerminal", target = "arrivalTerminal")
    @Mapping(source = "flightDetails.gateNumber", target = "gateNumber")
    @Mapping(source = "flightDetails.maxBaggageWeight", target = "maxBaggageWeight")
    @Mapping(source = "flightDetails.isWifiAvailable", target = "isWifiAvailable")
    @Mapping(source = "flightDetails.availableSeats", target = "availableSeats")
    @Mapping(source = "flightDetails.maxSeats", target = "maxSeats")
    @Mapping(source = "flightDetails.status", target = "flightStatus")
    @Mapping(source = "departureCity", target = "departureCity")
    @Mapping(source = "arrivalCity", target = "arrivalCity")
    FlightDto toFlightDto(FlightEntity flightEntity);

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "flightDetails", source = "request", qualifiedByName = "mapFlightDetails")
    FlightEntity toEntity(CreateFlightRequest request);

    @Named("mapFlightDetails")
    default FlightDetailsEntity mapFlightDetails(CreateFlightRequest request) {
        if (request == null) {
            return null;
        }
        return FlightDetailsEntity.builder()
                .aircraftModel(request.getAircraftModel())
                .departureTerminal(request.getDepartureTerminal())
                .arrivalTerminal(request.getArrivalTerminal())
                .gateNumber(request.getGateNumber())
                .maxBaggageWeight(request.getMaxBaggageWeight())
                .isWifiAvailable(request.getIsWifiAvailable())
                .availableSeats(request.getAvailableSeats())
                .maxSeats(request.getMaxSeats())
                .status(request.getFlightStatus())
                .build();
    }

    @Mapping(target = "updatedBy", source = "updateFlightRequest.adminId")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "airlineName", source = "updateFlightRequest.airlineName")
    @Mapping(target = "departureTime", source = "updateFlightRequest.departureTime")
    @Mapping(target = "arrivalTime", source = "updateFlightRequest.arrivalTime")
    @Mapping(target = "departureAirport", source = "updateFlightRequest.departureAirport")
    @Mapping(target = "arrivalAirport", source = "updateFlightRequest.arrivalAirport")
    @Mapping(target = "departureCity", source = "updateFlightRequest.departureCity")
    @Mapping(target = "arrivalCity", source = "updateFlightRequest.arrivalCity")
    @Mapping(target = "flightDetails", source = "updateFlightRequest")
    FlightEntity updateFlightEntityFromRequest(@MappingTarget FlightEntity existingFlight, UpdateFlightRequest updateFlightRequest);

    @Mapping(target = "aircraftModel", source = "updateFlightRequest.aircraftModel")
    @Mapping(target = "departureTerminal", source = "updateFlightRequest.departureTerminal")
    @Mapping(target = "arrivalTerminal", source = "updateFlightRequest.arrivalTerminal")
    @Mapping(target = "gateNumber", source = "updateFlightRequest.gateNumber")
    @Mapping(target = "maxBaggageWeight", source = "updateFlightRequest.maxBaggageWeight")
    @Mapping(target = "isWifiAvailable", source = "updateFlightRequest.isWifiAvailable")
    @Mapping(target = "availableSeats", source = "updateFlightRequest.availableSeats")
    @Mapping(target = "maxSeats", source = "updateFlightRequest.maxSeats")
    @Mapping(target = "status", source = "updateFlightRequest.flightStatus")
    void updateFlightDetailsFromRequest(@MappingTarget FlightDetailsEntity flightDetails, UpdateFlightRequest updateFlightRequest);
}
