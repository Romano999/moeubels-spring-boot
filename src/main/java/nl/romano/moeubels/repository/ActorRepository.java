package nl.romano.moeubels.repository;

import nl.romano.moeubels.model.Actor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface ActorRepository extends PagingAndSortingRepository<Actor, UUID> {
    Actor findByUsername(String username);
}
