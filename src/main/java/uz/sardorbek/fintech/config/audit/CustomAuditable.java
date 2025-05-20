package uz.sardorbek.fintech.config.audit;


import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.sardorbek.fintech.config.utils.abstract_entity.AbstractAuditEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class CustomAuditable {
    String name;

    @PrePersist
    public void prePersist(Object entity) {
        name = getCurrentUsername();
        log.info("|{}|CREATE|name:{}|BY:{}|at:{}", getClassOfObject((AbstractAuditEntity) entity), getObjectName((AbstractAuditEntity) entity), name, getDateNow());
    }

    @PreUpdate
    public void preUpdate(Object entity) {
        name = getCurrentUsername();
        log.info("|{}|UPDATE|id:{} name:{}|BY:{}|at:{}", getClassOfObject((AbstractAuditEntity) entity), getObjectId((AbstractAuditEntity) entity), getObjectName((AbstractAuditEntity) entity), name, getDateNow());
    }

    @PreRemove
    public void preRemove(Object entity) {
        name = getCurrentUsername();
        log.info("|{}|DELETE|id:{} name:{}|BY:{}|AT:{}", getClassOfObject((AbstractAuditEntity) entity), getObjectId((AbstractAuditEntity) entity), getObjectName((AbstractAuditEntity) entity), name, getDateNow());
    }

    public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null && auth.getName() != null) ? auth.getName() : "SYSTEM";
    }

    private String getClassOfObject(AbstractAuditEntity abstractAuditEntity) {
        return abstractAuditEntity.getClassName();
    }

    private Long getObjectId(AbstractAuditEntity abstractAuditEntity) {
        return abstractAuditEntity.getId();
    }

    private String getObjectName(AbstractAuditEntity abstractAuditEntity) {
        return abstractAuditEntity.getName()!=null?abstractAuditEntity.getName():"default name";
    }

    private String getDateNow() {
        LocalDateTime currentTime = LocalDateTime.now();
        return currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
