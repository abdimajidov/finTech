package uz.sardorbek.fintech.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.sardorbek.fintech.config.utils.global_response.ApiResponse;
import uz.sardorbek.fintech.user.model.criteria.UserCriteria;
import uz.sardorbek.fintech.user.model.dto.UserDTO;
import uz.sardorbek.fintech.user.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Tag(name = "User")
public class UserController {
    final UserService userService;
    final static String BY_ID = "/{id}";
    final static String LIST = "/list";
    final static String USER_ROLES = "/roles";

    @PostMapping
    @Operation(summary = "Add user", description = "This method can help you to add new user.")
    public ResponseEntity<?> add(@RequestBody @Valid UserDTO userDTO) {
        ApiResponse response = userService.add(userDTO);
        return ResponseEntity.status(response.getHttpStatus()).body(response.getPayload());
    }

    @GetMapping
    @Operation(summary = "Get users", description = "This method can help you to get users.")
    public ResponseEntity<?> get(UserCriteria criteria, @ParameterObject Pageable pageable) {
        ApiResponse response = userService.get(criteria, pageable);
        return ResponseEntity.status(response.getHttpStatus()).body(response.getPayload());
    }

    @GetMapping(LIST)
    @Operation(summary = "Get users list", description = "This method can help you to get users list.")
    public ResponseEntity<?> getList() {
        ApiResponse response = userService.getList();
        return ResponseEntity.status(response.getHttpStatus()).body(response.getPayload());
    }

    @GetMapping(BY_ID)
    @Operation(summary = "Get user by id", description = "This method can help you to get user by id.")
    public ResponseEntity<?> get(@PathVariable Long id) {
        ApiResponse response = userService.getById(id);
        return ResponseEntity.status(response.getHttpStatus()).body(response.getPayload());
    }

    @GetMapping(USER_ROLES)
    @Operation(summary = "Get user roles", description = "This method can help you to get user roles.")
    public ResponseEntity<?> getRoles() {
        ApiResponse response = userService.getRoles();
        return ResponseEntity.status(response.getHttpStatus()).body(response.getPayload());
    }

    @PutMapping(value = BY_ID)
    @Operation(summary = "Edit user", description = "This method can help you to edit new user.")
    public ResponseEntity<?> edit(@PathVariable Long id,
                                  @RequestBody @Valid UserDTO.UserUpdateDTO userDTO) {
        ApiResponse response = userService.edit(id, userDTO);
        return ResponseEntity.status(response.getHttpStatus()).body(response.getPayload());
    }

    @DeleteMapping(BY_ID)
    @Operation(summary = "Lock user", description = "This method can help you to lock user by id.")
    public ResponseEntity<?> lock(@PathVariable Long id) {
        ApiResponse response = userService.lock(id);
        return ResponseEntity.status(response.getHttpStatus()).body(response.getPayload());
    }

    @PatchMapping(BY_ID)
    @Operation(summary = "Unlock user", description = "This method can help you to Unlock user by id.")
    public ResponseEntity<?> unlock(@PathVariable Long id) {
        ApiResponse response = userService.unlock(id);
        return ResponseEntity.status(response.getHttpStatus()).body(response.getPayload());
    }
}
