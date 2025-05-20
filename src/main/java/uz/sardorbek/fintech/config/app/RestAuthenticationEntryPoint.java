package uz.sardorbek.fintech.config.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException authException) throws IOException {
        Map<String, Object> map = new HashMap<>();
        if (authException instanceof BadCredentialsException) {
            map.put("message", "Bad Credentials");
            httpServletResponse.setStatus(400);
        } else if (authException instanceof InsufficientAuthenticationException) {
            map.put("message", "Not Authorized");
            httpServletResponse.setStatus(401);
        } else if (authException instanceof LockedException) {
            map.put("message", "Account Locked");
            httpServletResponse.setStatus(401);
        } else if (authException instanceof DisabledException) {
            map.put("message", "Account Disabled");
            httpServletResponse.setStatus(401);
        }
        map.put("url", httpServletRequest.getRequestURL().toString());
        httpServletResponse.setContentType("application/json");
        OutputStream out = httpServletResponse.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, map);
        out.flush();
    }

}
