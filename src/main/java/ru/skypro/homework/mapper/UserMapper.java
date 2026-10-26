package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;
import ru.skypro.homework.controller.dto.RegisterDTO;
import ru.skypro.homework.controller.dto.UpdateUserDTO;
import ru.skypro.homework.controller.dto.UserDTO;
import ru.skypro.homework.db.entity.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mappings(value = {
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "email", source = "userName"),
            @Mapping(target = "image", ignore = true)
    })
    User registerDTOToUser(RegisterDTO register);

    UserDTO userToUserDTO(User user);

    @Mappings(value = {
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "email", ignore = true),
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "role", ignore = true),
            @Mapping(target = "image", ignore = true)
    })
    User updateUserDTOToUser(UpdateUserDTO userDTO);

}
