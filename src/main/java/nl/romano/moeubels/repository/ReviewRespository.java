package nl.romano.moeubels.repository;

import nl.romano.moeubels.model.Review;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.UUID;

public interface ReviewRespository extends PagingAndSortingRepository<Review, UUID> {
    @Query(
            value = "SELECT * FROM review AS r WHERE CAST(r.product_id AS TEXT) LIKE CAST(?1 AS TEXT)",
            nativeQuery = true
    )
    List<Review> findByProductId(UUID actorId);
}
