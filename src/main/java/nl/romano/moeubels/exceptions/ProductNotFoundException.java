package nl.romano.moeubels.exceptions;

public class ProductNotFoundException extends ResourceNotFoundException{
    public ProductNotFoundException(String message) {
        super(message);
    }
}
