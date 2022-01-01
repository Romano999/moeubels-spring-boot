package nl.romano.moeubels.dao;

import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import java.util.Optional;
import java.util.UUID;

/**
 * A class can implement this interface to receive REST methods.
 */
public interface Dao<T> {
    Optional<T> getById(UUID uuid);
    void save(T t);
    void update(T t);
    void delete(UUID uuid) throws ResourceNotFoundException ;
}
