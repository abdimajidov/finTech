package uz.sardorbek.fintech.user.service;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.sardorbek.fintech.config.utils.global_response.ApiResponse;
import uz.sardorbek.fintech.config.utils.global_response.GlobalResponse;
import uz.sardorbek.fintech.user.model.criteria.UserCriteria;
import uz.sardorbek.fintech.user.model.dto.UserDTO;
import uz.sardorbek.fintech.user.model.dto.UserListDTO;
import uz.sardorbek.fintech.user.model.entity.User;
import uz.sardorbek.fintech.user.model.enums.Roles;
import uz.sardorbek.fintech.user.model.mapper.UserMapper;
import uz.sardorbek.fintech.user.repository.RoleRepository;
import uz.sardorbek.fintech.user.repository.UserRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService {

    final GlobalResponse globalResponse;
    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;
    final UserMapper userMapper;
    final RoleRepository roleRepository;
    final UserQueryService userQueryService;
    final RoleService roleService;

    public void initializeUser() {
        List<User> userList = List.of(
                User.builder().username("admin@root").
                        password(passwordEncoder.encode("123"))
                        .address("Uzbekistan, Tashkent")
                        .email("admin@mail.ru")
                        .name("Super")
                        .surname("Administrator")
                        .patronym("Admin")
                        .role(roleRepository.findByName(Roles.ADMIN.name()).orElseThrow(() -> new NoSuchElementException("Admin Role not found")))
                        .balance(BigDecimal.ZERO)
                        .phoneNumber("998901234567")
                        .build(),
                User.builder().username("samandar").
                        password(passwordEncoder.encode("456"))
                        .address("Uzbekistan, Tashkent")
                        .email("samandar@mail.ru")
                        .name("Samandar")
                        .surname("Ochilov")
                        .role(roleRepository.findByName(Roles.ACCOUNTANT_ASSISTANT.name()).orElseThrow(() -> new NoSuchElementException("ACCOUNTANT_ASSISTANT Role not found")))
                        .balance(BigDecimal.valueOf(1000000))
                        .phoneNumber("998901234568")
                        .patronym("Aliyevich")
                        .build(),
                User.builder().username("ilhom").
                        password(passwordEncoder.encode("789"))
                        .address("Uzbekistan, Tashkent")
                        .email("ilhom@mail.ru")
                        .name("Ilhom")
                        .surname("Qamrayev")
                        .role(roleRepository.findByName(Roles.MANAGER.name()).orElseThrow(() -> new NoSuchElementException("MANAGER Role not found")))
                        .balance(BigDecimal.valueOf(1000000))
                        .phoneNumber("998901234569")
                        .patronym("Botirovich")
                        .build(),
                User.builder().username("doston").
                        password(passwordEncoder.encode("101"))
                        .address("Uzbekistan, Tashkent")
                        .email("doston@mail.ru")
                        .name("Doston")
                        .surname("Mavlonov")
                        .role(roleRepository.findByName(Roles.HOUSEHOLD_EMPLOYEE.name()).orElseThrow(() -> new NoSuchElementException("HOUSEHOLD_EMPLOYEE Role not found")))
                        .balance(BigDecimal.valueOf(1000000))
                        .phoneNumber("998901234560")
                        .patronym("Toxirovich")
                        .build()
        );
        userRepository.saveAll(userList);
    }

    public ApiResponse add(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.password()));
        User savedUser = userRepository.save(user);
        return globalResponse.responseCreatedStatus(savedUser);
    }

    public ApiResponse get(UserCriteria criteria, Pageable pageable) {
        return userQueryService.findByCriteria(criteria, pageable);
    }

    public ApiResponse getRoles() {
        return globalResponse.responseOKStatus(roleService.findAll());
    }

    public ApiResponse edit(Long id, @Valid UserDTO.UserUpdateDTO userDTO) {
        User user = findById(id);
        User authEmployee = globalResponse.getAuthUser();
        return authEmployee.getRole().getName().equals(Roles.ADMIN.name()) ? editWithPermissionAdmin(user, userDTO) : editWithPermissionOther(authEmployee, user, userDTO);
    }

    private ApiResponse editWithPermissionOther(User authEmployee, User user, @Valid UserDTO.UserUpdateDTO userDTO) {
        if (!Objects.equals(authEmployee.getId(), user.getId())) {
            return globalResponse.responseBadRequest("You are not allowed to edit this User");
        }
        user.setEmail(userDTO.email());
        user.setPhoneNumber(userDTO.phoneNumber());
        userRepository.save(user);
        return globalResponse.responseEditedStatus(user);
    }

    private ApiResponse editWithPermissionAdmin(User user, @Valid UserDTO.UserUpdateDTO userDTO) {
        userMapper.updateFromDto(userDTO, user);
        if (userDTO.password() != null) {
            user.setPassword(passwordEncoder.encode(userDTO.password()));
        }
        userRepository.save(user);
        return globalResponse.responseEditedStatus(user);
    }

    public ApiResponse lock(Long id) {
        User user = findById(id);
        user.setIsActive(false);
        userRepository.save(user);
        return globalResponse.responseEditedStatus();
    }

    public ApiResponse getById(Long id) {
        return globalResponse.responseOKStatus(findById(id));
    }

    public ApiResponse getList() {
        return globalResponse.responseOKStatus(userRepository.findAllByIsActiveTrue().stream().map(user -> new UserListDTO(user.getId(), user.getName() + (user.getSurname() != null ? (" " + user.getSurname()) : "") + (user.getPatronym() != null ? (" " + user.getPatronym()) : ""))).toList());
    }

    public ApiResponse unlock(Long id) {
        User user = findById(id);
        user.setIsActive(true);
        userRepository.save(user);
        return globalResponse.responseEditedStatus();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found by id: " + id));
    }
}
