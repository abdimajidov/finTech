package uz.sardorbek.fintech.user.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import uz.sardorbek.fintech.user.model.entity.Role;
import uz.sardorbek.fintech.user.model.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByUsername(String username);
    List<User> findAllByIdInAndIsActiveTrue(List<Long> ids);
    List<User> findAllByIsActiveTrueAndIsEnabledTrue();
    List<User> findAllByIsActiveTrueAndIsEnabledTrueAndUsernameIsNot(String username);
    Boolean existsByRole(Role role);
    int countAllByIsActiveTrueAndIsEnabledTrue();
}