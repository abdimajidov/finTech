package uz.sardorbek.fintech.config.utils.global_response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ResponseObject {
    String message;
    Object object;

    public ResponseObject(String message) {
        this.message = message;
    }
}
