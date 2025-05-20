package uz.sardorbek.fintech.config.handler.exception.customException;

public class ElementNotFoundException extends Exception{
    public ElementNotFoundException(String element) {
        super(element);
    }
}