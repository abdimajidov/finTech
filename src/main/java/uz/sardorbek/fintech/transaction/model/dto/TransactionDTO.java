package uz.sardorbek.fintech.transaction.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransactionDTO(
        @NotNull(message = "Sender is required")
        Long senderId,
        @NotNull(message = "Receiver is required")
        Long receiverId,
        @NotNull(message = "Amount is required")
        BigDecimal amount,
        @NotBlank(message = "Description is required")
        String description
) {
}
