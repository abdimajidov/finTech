package uz.sardorbek.fintech.user.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Auditable;
import org.springframework.security.core.GrantedAuthority;
import uz.sardorbek.fintech.config.utils.abstract_entity.AbstractAuditEntity;

import java.util.HashSet;
import java.util.Set;


@EntityListeners(Auditable.class)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "className", "updated", "authority", "createdBy", "updatedBy"})
public class Role extends AbstractAuditEntity implements GrantedAuthority {
    @Column(unique = true)
    String name;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "roles_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<Permission> permissions = new HashSet<>();

    @Override
    public String getClassName() {
        return "ROLE";
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + getName();
    }

}
