package uz.sardorbek.fintech.user.model.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record RoleDTO(
        @NotBlank(message = "roleName can not be blank")
        String roleName,
        Set<Long> permissionIds
) {
    public record RoleUpdateDTO(@NotBlank(message = "roleName can not be blank") String roleName,
                                Set<Long> permissionIds) {
    }
}
