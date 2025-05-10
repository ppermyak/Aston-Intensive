package ru.aston.teamwork.utill;

import org.mapstruct.*;
import ru.aston.teamwork.dto.UserRequestDto;
import ru.aston.teamwork.dto.UserResponseDto;
import ru.aston.teamwork.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toUser(UserRequestDto userDto);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "createdAt", target = "createdAt")
    UserResponseDto toUserResponseDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateUserFromDto(UserRequestDto userDto, @MappingTarget User user);
}
