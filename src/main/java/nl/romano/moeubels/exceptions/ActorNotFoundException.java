package nl.romano.moeubels.exceptions;

public class ActorNotFoundException extends ResourceNotFoundException{
    public ActorNotFoundException(String message) {
        super(message);
    }
}
