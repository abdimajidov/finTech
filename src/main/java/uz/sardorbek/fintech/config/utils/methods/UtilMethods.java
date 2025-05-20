package uz.sardorbek.fintech.config.utils.methods;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UtilMethods {
    public boolean checkExpired(LocalDate expirationDate) {
        return expirationDate.isBefore(LocalDate.now());
    }

}
