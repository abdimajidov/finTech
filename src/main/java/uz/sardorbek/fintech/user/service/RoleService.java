package uz.sardorbek.fintech.user.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.sardorbek.fintech.user.model.entity.Role;
import uz.sardorbek.fintech.user.model.enums.Roles;
import uz.sardorbek.fintech.user.repository.RoleRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleService {
    final RoleRepository roleRepository;
    final PermissionService permissionService;

    public void initializeRoles() {
        for (Roles value : Roles.values()) {
            roleRepository.save(Role.builder().name(value.name()).permissions(permissionService.getPermissions()).build());
        }
    }

    public Role findById(Long roleId) {
        return roleRepository.findById(roleId).orElseThrow(() ->
                new IllegalArgumentException("Role not found for id: " + roleId));
    }

    public List<Role> findAll() {
        return roleRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }
}
