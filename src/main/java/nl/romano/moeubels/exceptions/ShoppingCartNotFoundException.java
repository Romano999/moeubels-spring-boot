package nl.romano.moeubels.exceptions;

public class ShoppingCartNotFoundException extends ResourceNotFoundException {
    public ShoppingCartNotFoundException(String message) {
        super(message);
    }
}
