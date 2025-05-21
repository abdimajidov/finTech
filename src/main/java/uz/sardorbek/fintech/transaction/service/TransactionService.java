package uz.sardorbek.fintech.transaction.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.sardorbek.fintech.config.handler.exception.customException.NotEnoughBalanceException;
import uz.sardorbek.fintech.config.utils.global_response.ApiResponse;
import uz.sardorbek.fintech.config.utils.global_response.GlobalResponse;
import uz.sardorbek.fintech.transaction.model.dto.TransactionDTO;
import uz.sardorbek.fintech.transaction.model.entity.Transaction;
import uz.sardorbek.fintech.transaction.repository.TransactionRepository;
import uz.sardorbek.fintech.user.model.entity.User;
import uz.sardorbek.fintech.user.repository.UserRepository;
import uz.sardorbek.fintech.user.service.UserService;

import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionService {
    UserService userService;
    UserRepository userRepository;
    TransactionRepository transactionRepository;
    private final GlobalResponse globalResponse;

    @SneakyThrows
    @Transactional(rollbackOn = Exception.class)
    public ApiResponse add(@Valid TransactionDTO transactionDTO) {
        if (transactionDTO.senderId().equals(transactionDTO.receiverId())) {
            throw new IllegalArgumentException(" Sender and Receiver Ids are the same");
        }
        User sender = userService.findById(transactionDTO.senderId());
        User receiver = userService.findById(transactionDTO.receiverId());
        if (sender.getBalance().compareTo(transactionDTO.amount()) < 0) {
            throw new NotEnoughBalanceException("Sender Balance is less than amount");
        }
        sender.setBalance(sender.getBalance().subtract(transactionDTO.amount()));
        receiver.setBalance(receiver.getBalance().add(transactionDTO.amount()));
        userRepository.save(sender);
        userRepository.save(receiver);

        Transaction transaction = Transaction.builder()
                .amount(transactionDTO.amount())
                .sender(sender)
                .receiver(receiver)
                .description(transactionDTO.description())
                .build();
        transactionRepository.save(transaction);
        return globalResponse.responseCreatedStatus();
    }

    public ApiResponse get(LocalDate from, LocalDate to, Pageable pageable) {
        if (from != null && to != null) {
            return globalResponse.responseOKStatus(transactionRepository.findAllByCreated_DateBetween(from, to, pageable));
        }
        return globalResponse.responseOKStatus(transactionRepository.findAll(pageable));
    }

    public ApiResponse get(Long userId, Pageable pageable) {
        User senderOrReceiver = userService.findById(userId);
        return globalResponse.responseOKStatus(transactionRepository.findAllBySenderOrReceiver(senderOrReceiver, senderOrReceiver, pageable));
    }
}
