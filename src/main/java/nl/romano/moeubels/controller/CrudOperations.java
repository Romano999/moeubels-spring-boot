package nl.romano.moeubels.controller;

import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import java.util.UUID;

/**
 * A class can implement this interface to support basic crud operations.
 * @param <T> generic
 */
public interface CrudOperations<T> {
    ResponseEntity<?> getById(UUID uuid) throws ResourceNotFoundException;
    ResponseEntity<String> create(T generic);
    ResponseEntity<String> update(T generic);
    ResponseEntity<?> delete(UUID uuid) throws ResourceNotFoundException;
}
