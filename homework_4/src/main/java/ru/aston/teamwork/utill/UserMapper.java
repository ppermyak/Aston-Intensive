package ru.aston.teamwork.utill;

import org.mapstruct.*;
import ru.aston.teamwork.dto.UserRequestDto;
import ru.aston.teamwork.dto.UserResponseDto;
import ru.aston.teamwork.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserRequestDto userDto);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    UserResponseDto toUserResponseDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UserRequestDto userDto, @MappingTarget User user);
}
