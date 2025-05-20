package uz.sardorbek.fintech.config.auth;

import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.sardorbek.fintech.user.model.entity.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponse {
    String token;
    Role role;
    String username;
    String fullName;
    Long id;
}
