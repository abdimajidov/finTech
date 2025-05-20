package uz.sardorbek.fintech.user.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import uz.sardorbek.fintech.config.utils.mapper.EntityMapper;
import uz.sardorbek.fintech.user.model.dto.UserDTO;
import uz.sardorbek.fintech.user.model.entity.User;
import uz.sardorbek.fintech.user.service.RoleService;

@Mapper(componentModel = "spring", uses = RoleService.class)
public interface UserMapper extends EntityMapper<UserDTO, User> {

    @Mapping(source = "roleId", target = "role")
    @Mapping(target = "password", ignore = true)
    User toEntity(UserDTO dto);

    @Mapping(target = "password", ignore = true)
    @Mapping(source = "roleId", target = "role")
    void updateFromDto(UserDTO.UserUpdateDTO dto, @MappingTarget User user);
}
