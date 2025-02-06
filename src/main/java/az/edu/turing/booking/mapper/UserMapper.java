package az.edu.turing.booking.mapper;

import az.edu.turing.booking.domain.entity.UserEntity;
import az.edu.turing.booking.model.dto.request.CreateUserRequest;
import az.edu.turing.booking.model.dto.request.UpdateUserDto;
import az.edu.turing.booking.model.dto.response.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toUserDto(UserEntity userEntity);

    UpdateUserDto toDto(UserEntity userEntity);

    @Mapping(target = "role", constant = "USER")  // Assuming Role is a constant "USER"
    @Mapping(target = "status", constant = "ACTIVE") // Assuming UserStatus is ACTIVE by default
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    UserEntity toUserEntity(CreateUserRequest request);

    @Mapping(target = "firstName", source = "updateUserDto.firstName")
    @Mapping(target = "lastName", source = "updateUserDto.lastName")
    @Mapping(target = "email", source = "updateUserDto.email")
    @Mapping(target = "phoneNumber", source = "updateUserDto.phoneNumber")
    @Mapping(target = "dateOfBirth", source = "updateUserDto.dateOfBirth")
    @Mapping(target = "nationality", source = "updateUserDto.nationality")
    @Mapping(target = "role", source = "updateUserDto.role")
    @Mapping(target = "status", source = "updateUserDto.status")
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    UserEntity updateUserEntityFromDto(@MappingTarget UserEntity userEntity, UpdateUserDto updateUserDto);
}
