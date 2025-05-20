package uz.sardorbek.fintech;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@OpenAPIDefinition(
        servers = {@Server(url = "/", description = "server URL https://fintech.sardor.uz/app")},
        info = @Info(title = "Fin Tech API", version = "1.0", description = "API Information")
)
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableScheduling
@EnableAsync
public class FinTechApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinTechApplication.class, args);
    }
}
