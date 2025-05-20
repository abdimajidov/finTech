package uz.sardorbek.fintech.config.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import uz.sardorbek.fintech.user.model.entity.User;


@Configuration
public class AuditConfig {
    @Bean
    AuditorAware<User> auditorAware() {
        return new SpringSecurityAuditAwareImpl();
    }
}
