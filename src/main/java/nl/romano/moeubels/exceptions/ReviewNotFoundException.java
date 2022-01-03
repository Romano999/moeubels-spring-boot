package nl.romano.moeubels.exceptions;

public class ReviewNotFoundException extends ResourceNotFoundException{
    public ReviewNotFoundException(String message) {
        super(message);
    }
}
