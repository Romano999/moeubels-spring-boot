package nl.romano.moeubels.exceptions;

public class CategoryNotFoundException extends ResourceNotFoundException{
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
