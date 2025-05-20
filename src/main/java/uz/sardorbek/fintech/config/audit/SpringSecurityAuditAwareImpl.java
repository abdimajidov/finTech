package uz.sardorbek.fintech.config.audit;


import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.sardorbek.fintech.user.model.entity.User;

import java.util.Optional;

@Component
public class SpringSecurityAuditAwareImpl implements AuditorAware<User> {

    @NotNull
    @Override
    public Optional<User> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null
                && authentication.isAuthenticated()
                && !authentication.getPrincipal().equals("anonymousUser")) {
            User user = (User) authentication.getPrincipal();
            return Optional.of(user);
        }
        return Optional.empty();
    }
}
