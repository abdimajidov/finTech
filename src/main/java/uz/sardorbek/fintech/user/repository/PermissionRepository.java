package uz.sardorbek.fintech.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.sardorbek.fintech.user.model.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
