package uz.sardorbek.fintech.user.model.enums;

import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;


public enum Permissions implements GrantedAuthority {

    /**
     * BASIC PERMISSION
     */

    CREATE,
    READ,
    UPDATE,
    DELETE;

    public static Set<Permissions> permissionsSet(Roles role) {
        return Arrays.stream(Permissions.values()).collect(Collectors.toSet());
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
