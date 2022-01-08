package nl.romano.moeubels.repository;

import nl.romano.moeubels.model.Favourite;
import nl.romano.moeubels.model.FavouriteCK;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.UUID;

public interface FavouriteRepository extends PagingAndSortingRepository<Favourite, FavouriteCK> {
    @Query(
            value = "SELECT * FROM favourite AS s WHERE CAST(s.actor_id AS TEXT) LIKE CAST(?1 AS TEXT)",
            nativeQuery = true
    )
    List<Favourite> getByActorId(UUID actorId);
}
