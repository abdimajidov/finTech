package uz.sardorbek.fintech.config.handler.exception.customException;

public class NotEnoughBalanceException extends Exception {
    public NotEnoughBalanceException(String message) {
        super(message);
    }
}
