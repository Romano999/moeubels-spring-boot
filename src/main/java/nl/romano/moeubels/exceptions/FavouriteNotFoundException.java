package nl.romano.moeubels.exceptions;

public class FavouriteNotFoundException extends ResourceNotFoundException {
    public FavouriteNotFoundException(String message) {
        super(message);
    }
}
