package uz.sardorbek.fintech.config.auth;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.sardorbek.fintech.config.utils.global_response.ApiResponse;
import uz.sardorbek.fintech.config.utils.global_response.ResponseObject;
import uz.sardorbek.fintech.user.model.entity.User;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationService {

    final JwtService jwtService;
    final AuthenticationManager authenticationManager;

    public ApiResponse authenticate(AuthenticationController.AuthenticationRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.username(),
                            request.password()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = (User) authentication.getPrincipal();
            String jwtToken = jwtService.generateToken(user);

            AuthenticationResponse authResponse = AuthenticationResponse.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .fullName((user.getName() == null || user.getSurname() == null) ? "Administrator" : user.getSurname() + " " + user.getName())
                    .token(jwtToken)
                    .role(user.getRole())
                    .build();
            return new ApiResponse(HttpStatus.OK, new ResponseObject("Success!", authResponse));
        } catch (AuthenticationException e) {
            return new ApiResponse(HttpStatus.UNAUTHORIZED, new ResponseObject(e.getMessage()));
        }
    }
}
