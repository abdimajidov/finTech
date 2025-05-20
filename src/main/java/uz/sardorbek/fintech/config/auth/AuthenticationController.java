package uz.sardorbek.fintech.config.auth;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.sardorbek.fintech.config.utils.global_response.ApiResponse;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    @Operation(summary = "This method helps you to authenticate user")
    public ResponseEntity<?> auth(@RequestBody AuthenticationRequest request) {
        ApiResponse response = authenticationService.authenticate(request);
        return ResponseEntity
                .status(response.getHttpStatus())
                .body(response.getPayload());
    }

    public record AuthenticationRequest(String username, String password) {
    }
}
