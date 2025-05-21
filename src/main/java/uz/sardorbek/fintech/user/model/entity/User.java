package uz.sardorbek.fintech.user.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.sardorbek.fintech.config.audit.CustomAuditable;
import uz.sardorbek.fintech.config.utils.abstract_entity.AbstractAuditEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners({AuditingEntityListener.class, CustomAuditable.class})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "className", "updated", "createdBy", "updatedBy", "authorities"})
public class User extends AbstractAuditEntity implements UserDetails, Serializable {
    String name;
    String surname;
    String patronym;
    String phoneNumber;
    String address;

    @Column(unique = true, nullable = false)
    String username;
    @JsonIgnore
    String password;
    @Column(unique = true, nullable = false)
    String email;

    @Column(nullable = false)
    BigDecimal balance = BigDecimal.ZERO;

    @JsonIgnore
    Boolean isAccountNonExpired;
    Boolean isActive;

    @JsonIgnore
    Boolean isCredentialsNonExpired;

    @JsonIgnore
    Boolean isEnabled;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(() -> role.getAuthority());
        authorities.addAll(role.getPermissions());
        return authorities;
    }

    @PrePersist
    private void preCreate() {
        isAccountNonExpired = true;
        isActive = true;
        isCredentialsNonExpired = true;
        isEnabled = true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getClassName() {
        return "USER";
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return this.password;
    }

}