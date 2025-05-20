package uz.sardorbek.fintech.user.model.dto;

import uz.sardorbek.fintech.user.model.entity.Permission;

public record PermissionWithDetails(
        Permission permission,
        boolean isActive
) {
}
