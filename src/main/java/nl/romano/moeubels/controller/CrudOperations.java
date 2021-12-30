package nl.romano.moeubels.controller;

import org.springframework.http.ResponseEntity;
import java.util.UUID;

public interface CrudOperations<T> {
    ResponseEntity<?> getById(UUID uuid) throws Exception;
    ResponseEntity<String> create(T generic);
    ResponseEntity<String> update(T generic);
    ResponseEntity<?> delete(UUID uuid) throws Exception;
}
