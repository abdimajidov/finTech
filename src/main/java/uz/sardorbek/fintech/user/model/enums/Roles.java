package uz.sardorbek.fintech.user.model.enums;

import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.List;

public enum Roles implements GrantedAuthority {
    ADMIN,
    MANAGER,
    ACCOUNTANT_ASSISTANT,
    INTERN,
    HOUSEHOLD_EMPLOYEE;

    public static List<Roles> roleList() {
        return Arrays.asList(Roles.values());
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
