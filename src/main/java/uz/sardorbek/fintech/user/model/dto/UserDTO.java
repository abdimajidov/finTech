package uz.sardorbek.fintech.user.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserDTO(
        @NotBlank(message = "name can not be empty")
        String name,
        @NotBlank(message = "surname can not be empty")
        String surname,
        String patronym,
        @NotBlank(message = "phoneNumber can not be empty")
        @Pattern(regexp = "^\\+998(90|91|93|94|95|96|97|98|99|50|88|33|77)\\d{7}$",
                message = "Invalid phone number format! Must be in +998901234567 format")
        String phoneNumber,
        String address,
        @NotBlank(message = "username can not be empty")
        String username,
        @NotBlank(message = "password can not be empty")
        String password,
        @NotBlank(message = "email can not be empty")
        @Email(message = "incorrect email input")
        String email,
        @NotNull(message = "role id is required")
        Long roleId
) {
    public record UserUpdateDTO(
            @NotBlank(message = "name can not be empty")
            String name,
            @NotBlank(message = "surname can not be empty")
            String surname,
            String patronym,
            @NotBlank(message = "phoneNumber can not be empty")
            @Pattern(regexp = "^\\+998(90|91|93|94|95|96|97|98|99|50|88|33|77)\\d{7}$",
                    message = "Invalid phone number format! Must be in +998901234567 format")
            String phoneNumber,
            String address,
            @NotBlank(message = "username can not be empty")
            String username,
            String password,
            @NotBlank(message = "email can not be empty")
            @Email(message = "incorrect email input")
            String email,
            @NotNull(message = "role id is required")
            Long roleId
    ) {
    }
}
