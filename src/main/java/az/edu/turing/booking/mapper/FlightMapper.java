package az.edu.turing.booking.mapper;

import az.edu.turing.booking.domain.entity.FlightDetailEntity;
import az.edu.turing.booking.domain.entity.FlightEntity;
import az.edu.turing.booking.model.dto.request.CreateFlightRequest;
import az.edu.turing.booking.model.dto.request.UpdateFlightRequest;
import az.edu.turing.booking.model.dto.response.FlightDto;
import az.edu.turing.booking.model.dto.response.UpdateFlightResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FlightMapper {

    FlightMapper INSTANCE = Mappers.getMapper(FlightMapper.class);

    @Mapping(source = "flightDetail.aircraftModel", target = "aircraftModel")
    @Mapping(source = "flightDetail.departureTerminal", target = "departureTerminal")
    @Mapping(source = "flightDetail.arrivalTerminal", target = "arrivalTerminal")
    @Mapping(source = "flightDetail.gateNumber", target = "gateNumber")
    @Mapping(source = "flightDetail.maxBaggageWeight", target = "maxBaggageWeight")
    @Mapping(source = "flightDetail.isWifiAvailable", target = "isWifiAvailable")
    @Mapping(source = "flightDetail.availableSeats", target = "availableSeats")
    @Mapping(source = "flightDetail.maxSeats", target = "maxSeats")
    @Mapping(source = "flightDetail.status", target = "flightStatus")
    UpdateFlightResponse toFlightResponseDto(FlightEntity flightEntity);

    @Mapping(source = "flightDetail.aircraftModel", target = "aircraftModel")
    @Mapping(source = "flightDetail.departureTerminal", target = "departureTerminal")
    @Mapping(source = "flightDetail.arrivalTerminal", target = "arrivalTerminal")
    @Mapping(source = "flightDetail.gateNumber", target = "gateNumber")
    @Mapping(source = "flightDetail.maxBaggageWeight", target = "maxBaggageWeight")
    @Mapping(source = "flightDetail.isWifiAvailable", target = "isWifiAvailable")
    @Mapping(source = "flightDetail.availableSeats", target = "availableSeats")
    @Mapping(source = "flightDetail.maxSeats", target = "maxSeats")
    @Mapping(source = "flightDetail.status", target = "flightStatus")
    @Mapping(source = "departureCity", target = "departureCity")
    @Mapping(source = "arrivalCity", target = "arrivalCity")
    FlightDto toFlightDto(FlightEntity flightEntity);

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "flightDetail", source = "request", qualifiedByName = "mapFlightDetail")
    FlightEntity toEntity(CreateFlightRequest request);

    @Named("mapFlightDetail")
    default FlightDetailEntity mapFlightDetail(CreateFlightRequest request) {
        if (request == null) {
            return null;
        }
        return FlightDetailEntity.builder()
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

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "airlineName", source = "updateFlightRequest.airlineName")
    @Mapping(target = "departureTime", source = "updateFlightRequest.departureTime")
    @Mapping(target = "arrivalTime", source = "updateFlightRequest.arrivalTime")
    @Mapping(target = "departureAirport", source = "updateFlightRequest.departureAirport")
    @Mapping(target = "arrivalAirport", source = "updateFlightRequest.arrivalAirport")
    @Mapping(target = "departureCity", source = "updateFlightRequest.departureCity")
    @Mapping(target = "arrivalCity", source = "updateFlightRequest.arrivalCity")
    @Mapping(target = "flightDetail", source = "updateFlightRequest")
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
    void updateFlightDetailsFromRequest(@MappingTarget FlightDetailEntity flightDetails, UpdateFlightRequest updateFlightRequest);
}
