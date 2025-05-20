package uz.sardorbek.fintech.config.utils.data_loader;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uz.sardorbek.fintech.user.service.PermissionService;
import uz.sardorbek.fintech.user.service.RoleService;
import uz.sardorbek.fintech.user.service.UserService;

@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DataLoader implements CommandLineRunner {
    final UserService userService;
    final PermissionService permissionService;
    final RoleService roleService;
    String ddlType;

    @Autowired
    void setDdlType(@Value("${spring.jpa.hibernate.ddl-auto}") String ddlType) {
        this.ddlType = ddlType;
    }


    @Override
    public void run(String... args) {
        log.info("Loading data...");
        if (ddlType.equals("create")) {
            permissionService.initialize();
            roleService.initializeRoles();
            userService.initializeUser();
        }

    }
}

