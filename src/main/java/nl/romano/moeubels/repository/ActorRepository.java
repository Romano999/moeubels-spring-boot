package nl.romano.moeubels.repository;

import nl.romano.moeubels.model.Actor;
import org.springframework.data.repository.CrudRepository;
import java.util.UUID;

public interface ActorRepository extends CrudRepository<Actor, UUID> {
}
