package uz.sardorbek.fintech.transaction.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.sardorbek.fintech.config.utils.global_response.ApiResponse;
import uz.sardorbek.fintech.transaction.model.dto.TransactionDTO;
import uz.sardorbek.fintech.transaction.service.TransactionService;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/transaction")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Tag(name = "Transaction")
public class TransactionController {
    final static String BY_USER_ID = "/user/{userId}";
    TransactionService transactionService;

    @PostMapping
    @Operation(summary = "Create transaction", description = "This method can help you to create new transaction.")
    public ResponseEntity<?> create(@RequestBody @Valid TransactionDTO transactionDTO) {
        ApiResponse response = transactionService.add(transactionDTO);
        return ResponseEntity.status(response.getHttpStatus()).body(response.getPayload());
    }

    @GetMapping
    @Operation(summary = "Get transactions", description = "This method can help you to get transactions.")
    public ResponseEntity<?> get(@ParameterObject Pageable pageable,
                                 @RequestParam(name = "from", required = false)
                                 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
                                 LocalDate from,
                                 @RequestParam(name = "to", required = false)
                                 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
                                 LocalDate to) {
        ApiResponse response = transactionService.get(from, to, pageable);
        return ResponseEntity.status(response.getHttpStatus()).body(response.getPayload());
    }

    @GetMapping(BY_USER_ID)
    @Operation(summary = "Get transactions by user Id", description = "This method can help you to get transactions by user Id.")
    public ResponseEntity<?> get(@PathVariable Long userId, @ParameterObject Pageable pageable) {
        ApiResponse response = transactionService.get(userId, pageable);
        return ResponseEntity.status(response.getHttpStatus()).body(response.getPayload());
    }
}
