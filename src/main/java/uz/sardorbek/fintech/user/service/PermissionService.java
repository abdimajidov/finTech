package uz.sardorbek.fintech.user.service;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.sardorbek.fintech.config.utils.global_response.ApiResponse;
import uz.sardorbek.fintech.config.utils.global_response.GlobalResponse;
import uz.sardorbek.fintech.user.model.dto.RoleDTO;
import uz.sardorbek.fintech.user.model.entity.Permission;
import uz.sardorbek.fintech.user.model.entity.Role;
import uz.sardorbek.fintech.user.model.enums.Permissions;
import uz.sardorbek.fintech.user.repository.PermissionRepository;
import uz.sardorbek.fintech.user.repository.RoleRepository;
import uz.sardorbek.fintech.user.repository.UserRepository;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionService {
    final PermissionRepository permissionRepository;
    final RoleRepository roleRepository;
    final GlobalResponse globalResponse;
    final UserRepository userRepository;

    public Permission findById(Long id) {
        return permissionRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Permission not found with id: " + id));
    }

    public Set<Permission> getPermissions() {
        return new HashSet<>(permissionRepository.findAll());
    }

    public ApiResponse addPermissionToRole(Long roleId, Long permissionId) {
        Permission permission = findById(permissionId);
        Role role = addPermissionToRole(roleId, permission);
        return globalResponse.responseCreatedStatus(role);
    }

    public ApiResponse removePermissionFromRole(Long roleId, Long permissionId) {
        Permission permission = findById(permissionId);
        removePermissionFromRole(roleId, permission);
        return globalResponse.responseOKStatus();
    }

    private Role addPermissionToRole(Long roleId, Permission permission) {
        Role role = findRoleById(roleId);
        role.getPermissions().add(permission);
        roleRepository.save(role);
        return role;
    }

    private void removePermissionFromRole(Long roleId, Permission permission) {
        Role role = findRoleById(roleId);
        role.getPermissions().removeIf(permission::equals);
        roleRepository.save(role);
    }

    private Role findRoleById(Long roleId) {
        return roleRepository.findById(roleId).orElseThrow(() ->
                new IllegalArgumentException("Role not found for id: " + roleId));
    }

    public void initialize() {
        List<Permission> permissions = new ArrayList<>();
        for (Permissions action : Permissions.values()) {
            permissions.add(Permission.builder()
                    .name(action.name())
                    .build());
        }
        permissionRepository.saveAll(permissions);
    }

    public ApiResponse getRoleById(Long roleId) {
        Role role = findRoleById(roleId);
        return globalResponse.responseOKStatus(role);
    }

    public ApiResponse getPermissionById(Long permissionId) {
        Permission permission = findById(permissionId);
        return globalResponse.responseOKStatus(permission);
    }

    public ApiResponse editRole(Long roleId, @Valid RoleDTO.RoleUpdateDTO roleDTO) {
        Role role = findRoleById(roleId);
        role.setName(roleDTO.roleName());
        List<Permission> permissionList = permissionRepository.findAllById(roleDTO.permissionIds());
        Set<Permission> permissionSet = new HashSet<>(permissionList);
        permissionRepository.findAllById(roleDTO.permissionIds());
        role.getPermissions().clear();
        role.setPermissions(permissionSet);
        roleRepository.save(role);
        return globalResponse.responseEditedStatus(role);
    }

    public ApiResponse addRole(@Valid RoleDTO roleDTO) {
        List<Permission> permissionList = permissionRepository.findAllById(roleDTO.permissionIds());
        if (permissionList.isEmpty()) {
            throw new NoSuchElementException("Permission not found");
        }
        Role role = Role.builder().name(roleDTO.roleName()).permissions(new HashSet<>(permissionList)).build();
        roleRepository.save(role);
        return globalResponse.responseCreatedStatus(role);
    }

    public ApiResponse deleteRole(Long roleId) {
        Role role = findRoleById(roleId);
        if (userRepository.existsByRole(role)) {
            return globalResponse.responseBadRequest("Role is already being used by another user");
        }
        roleRepository.delete(role);
        return globalResponse.responseSuccessDelete(roleId);
    }

    private boolean isExistPermission(Long permissionId, Set<Permission> permissions) {
        for (Permission permission : permissions) {
            if (permission.getId().equals(permissionId)) {
                return true;
            }
        }
        return false;
    }
}
