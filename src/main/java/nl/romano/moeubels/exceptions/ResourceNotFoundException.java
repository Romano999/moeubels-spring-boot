package nl.romano.moeubels.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
