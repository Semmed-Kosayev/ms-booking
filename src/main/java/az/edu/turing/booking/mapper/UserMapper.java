package az.edu.turing.booking.mapper;

import az.edu.turing.booking.domain.entity.UserEntity;
import az.edu.turing.booking.model.dto.request.UpdateUserDto;
import az.edu.turing.booking.model.dto.response.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(UserEntity userEntity);

    UpdateUserDto toDto(UserEntity userEntity);

}
