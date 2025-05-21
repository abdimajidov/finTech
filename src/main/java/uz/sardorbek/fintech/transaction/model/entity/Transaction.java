package uz.sardorbek.fintech.transaction.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.sardorbek.fintech.config.audit.CustomAuditable;
import uz.sardorbek.fintech.config.utils.abstract_entity.AbstractAuditEntity;
import uz.sardorbek.fintech.user.model.entity.User;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners({AuditingEntityListener.class, CustomAuditable.class})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "className", "updated", "createdBy", "updatedBy"})
public class Transaction extends AbstractAuditEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    User receiver;

    @Column(nullable = false)
    BigDecimal amount;

    String description;

    @Override
    public String getClassName() {
        return "TRANSACTION";
    }
}
