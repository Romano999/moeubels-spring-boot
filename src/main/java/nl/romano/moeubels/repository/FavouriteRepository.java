package nl.romano.moeubels.repository;

import nl.romano.moeubels.model.Favourite;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.UUID;

public interface FavouriteRepository extends PagingAndSortingRepository<Favourite, UUID> {
//    @Query(
//            value = "SELECT * FROM favourite AS s " +
//                    "WHERE CAST(s.actor_id AS TEXT) LIKE CAST(?1 AS TEXT) " +
//                    "AND CAST(s.product_id AS TEXT) LIKE CAST(?2 AS TEXT)",
//            nativeQuery = true
//    )
//    Favourite getById(UUID actorId, UUID productId);

    @Query(
            value = "SELECT * FROM favourite AS s WHERE CAST(s.actor_id AS TEXT) LIKE CAST(?1 AS TEXT)",
            nativeQuery = true
    )
    List<Favourite> getByActorId(UUID actorId);
}
