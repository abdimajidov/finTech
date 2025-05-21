package uz.sardorbek.fintech.user.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.sardorbek.fintech.config.utils.global_response.ApiResponse;
import uz.sardorbek.fintech.config.utils.global_response.GlobalResponse;
import uz.sardorbek.fintech.user.model.criteria.UserCriteria;
import uz.sardorbek.fintech.user.model.entity.User;
import uz.sardorbek.fintech.user.repository.UserRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserQueryService {
    final UserRepository userRepository;
    final GlobalResponse globalResponse;

    public ApiResponse findByCriteria(UserCriteria criteria, Pageable pageable) {
        Specification<User> specification = createSpecification(criteria);
        specification = specification.and((root, query, builder) -> builder.isTrue(root.get("isActive")));
        specification = specification.and((root, query, builder) -> builder.isTrue(root.get("isEnabled")));
        Pageable sortedPageable = globalResponse.makeSorted(pageable);
        return globalResponse.responseOKStatus(userRepository.findAll(specification, sortedPageable));
    }

    private Specification<User> createSpecification(UserCriteria criteria) {
        Specification<User> specification = Specification.where(null);

        if (Objects.nonNull(criteria)) {
            if (criteria.getUsername() != null) {
                if (criteria.getUsername().getContains() != null) {
                    specification = specification.and((root, query, builder) ->
                            builder.like(builder.lower(root.get("username")), "%" + criteria.getUsername().getContains().toLowerCase() + "%"));
                }
                if (criteria.getUsername().getEquals() != null) {
                    specification = specification.and((root, query, builder) ->
                            builder.equal(root.get("username"), criteria.getUsername().getEquals()));
                }
            }

            if (criteria.getName() != null) {
                if (criteria.getName().getContains() != null) {
                    specification = specification.and((root, query, builder) ->
                            builder.like(builder.lower(root.get("name")), "%" + criteria.getName().getContains().toLowerCase() + "%"));
                }
                if (criteria.getName().getEquals() != null) {
                    specification = specification.and((root, query, builder) ->
                            builder.equal(root.get("name"), criteria.getName().getEquals()));
                }
            }

            if (criteria.getSurname() != null) {
                if (criteria.getSurname().getContains() != null) {
                    specification = specification.and((root, query, builder) ->
                            builder.like(builder.lower(root.get("surname")), "%" + criteria.getSurname().getContains().toLowerCase() + "%"));
                }
                if (criteria.getSurname().getEquals() != null) {
                    specification = specification.and((root, query, builder) ->
                            builder.equal(root.get("surname"), criteria.getSurname().getEquals()));
                }
            }

            if (criteria.getPatronymic() != null) {
                if (criteria.getPatronymic().getContains() != null) {
                    specification = specification.and((root, query, builder) ->
                            builder.like(builder.lower(root.get("patronym")), "%" + criteria.getPatronymic().getContains().toLowerCase() + "%"));
                }
                if (criteria.getSurname().getEquals() != null) {
                    specification = specification.and((root, query, builder) ->
                            builder.equal(root.get("patronym"), criteria.getPatronymic().getEquals()));
                }
            }

            if (criteria.getPhoneNumber() != null) {
                if (criteria.getPhoneNumber().getContains() != null) {
                    specification = specification.and((root, query, builder) ->
                            builder.like(builder.lower(root.get("phoneNumber")), "%" + criteria.getPhoneNumber().getContains().toLowerCase() + "%"));
                }
                if (criteria.getSurname().getEquals() != null) {
                    specification = specification.and((root, query, builder) ->
                            builder.equal(root.get("phoneNumber"), criteria.getPhoneNumber().getEquals()));
                }
            }

            if (criteria.getAddress() != null) {
                if (criteria.getAddress().getContains() != null) {
                    specification = specification.and((root, query, builder) ->
                            builder.like(builder.lower(root.get("address")), "%" + criteria.getAddress().getContains().toLowerCase() + "%"));
                }
                if (criteria.getSurname().getEquals() != null) {
                    specification = specification.and((root, query, builder) ->
                            builder.equal(root.get("address"), criteria.getAddress().getEquals()));
                }
            }

            if (criteria.getEmail() != null) {
                if (criteria.getEmail().getContains() != null) {
                    specification = specification.and((root, query, builder) ->
                            builder.like(builder.lower(root.get("email")), "%" + criteria.getEmail().getContains().toLowerCase() + "%"));
                }
                if (criteria.getSurname().getEquals() != null) {
                    specification = specification.and((root, query, builder) ->
                            builder.equal(root.get("email"), criteria.getEmail().getEquals()));
                }
            }

            if (criteria.getRoleId() != null) {
                if (criteria.getRoleId().getEquals() != null) {
                    specification = specification.and((root, query, cb) ->
                            cb.equal(root.get("role").get("id"), criteria.getRoleId().getEquals()));
                }
            }

            if (criteria.getActive() != null) {
                if (criteria.getActive().getEquals() != null) {
                    specification = specification.and((root, query, cb) ->
                            cb.equal(root.get("isActive"), criteria.getActive().getEquals()));
                }
            }
        }
        return specification;
    }
}
