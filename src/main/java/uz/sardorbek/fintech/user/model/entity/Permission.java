package uz.sardorbek.fintech.user.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Auditable;
import org.springframework.security.core.GrantedAuthority;
import uz.sardorbek.fintech.config.utils.abstract_entity.AbstractAuditEntity;

@EntityListeners(Auditable.class)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "className", "updated", "authority", "createdBy", "updatedBy"})
public class Permission extends AbstractAuditEntity implements GrantedAuthority {
    @Column(unique = true, nullable = false)
    String name;

    @Override
    public String getClassName() {
        return "PERMISSION";
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}
