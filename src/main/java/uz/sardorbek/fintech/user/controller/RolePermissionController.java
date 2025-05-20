package uz.sardorbek.fintech.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.sardorbek.fintech.config.utils.global_response.ApiResponse;
import uz.sardorbek.fintech.user.model.dto.RoleDTO;
import uz.sardorbek.fintech.user.service.PermissionService;

@RestController
@RequestMapping("/api/v1/role-permission")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RolePermissionController {
    final PermissionService permissionService;
    final static String ADD_PERMISSION_TO_ROLE = "/{roleId}/{permissionId}";
    final static String REMOVE_PERMISSION_FROM_ROLE = "/{roleId}/{permissionId}";
    final static String ROLE_BY_ID = "/role/{roleId}";
    final static String ROLE = "/role";
    final static String GET_PERMISSION_BY_ID = "/permission/{permissionId}";

    @PostMapping(ADD_PERMISSION_TO_ROLE)
    @Operation(summary = "Add permission to role", description = "This method can help you to add permission to role.")
    public ResponseEntity<?> addPermissionToRole(@PathVariable Long roleId, @PathVariable Long permissionId) {
        ApiResponse response = permissionService.addPermissionToRole(roleId, permissionId);
        return ResponseEntity.status(response.getHttpStatus()).body(response.getPayload());
    }

    @DeleteMapping(REMOVE_PERMISSION_FROM_ROLE)
    @Operation(summary = "Add permission to role", description = "This method can help you to add permission to role.")
    public ResponseEntity<?> removePermissionFromRole(@PathVariable Long roleId, @PathVariable Long permissionId) {
        ApiResponse response = permissionService.removePermissionFromRole(roleId, permissionId);
        return ResponseEntity.status(response.getHttpStatus()).body(response.getPayload());
    }

    @GetMapping(ROLE_BY_ID)
    @Operation(summary = "Get role by id", description = "This method can help you to get role by id.")
    public ResponseEntity<?> getRoleById(@PathVariable Long roleId) {
        ApiResponse response = permissionService.getRoleById(roleId);
        return ResponseEntity.status(response.getHttpStatus()).body(response.getPayload());
    }

    @GetMapping(GET_PERMISSION_BY_ID)
    @Operation(summary = "Get permission by id", description = "This method can help you to get permission by id.")
    public ResponseEntity<?> getPermissionById(@PathVariable Long permissionId) {
        ApiResponse response = permissionService.getPermissionById(permissionId);
        return ResponseEntity.status(response.getHttpStatus()).body(response.getPayload());
    }

    @PutMapping(ROLE_BY_ID)
    @Operation(summary = "Edit role by id", description = "This method can help you to edit role by id.")
    public ResponseEntity<?> editRole(@PathVariable Long roleId, @RequestBody @Valid RoleDTO.RoleUpdateDTO roleDTO) {
        ApiResponse response = permissionService.editRole(roleId, roleDTO);
        return ResponseEntity.status(response.getHttpStatus()).body(response.getPayload());
    }

    @PostMapping(ROLE)
    @Operation(summary = "Add role", description = "This method can help you to add role.")
    public ResponseEntity<?> addRole(@RequestBody @Valid RoleDTO roleDTO) {
        ApiResponse response = permissionService.addRole(roleDTO);
        return ResponseEntity.status(response.getHttpStatus()).body(response.getPayload());
    }

    @DeleteMapping(ROLE_BY_ID)
    @Operation(summary = "Delete role by id", description = "This method can help you to delete role by id.")
    public ResponseEntity<?> deleteRole(@PathVariable Long roleId) {
        ApiResponse response = permissionService.deleteRole(roleId);
        return ResponseEntity.status(response.getHttpStatus()).body(response.getPayload());
    }

}
