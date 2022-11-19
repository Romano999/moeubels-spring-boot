package nl.romano.moeubels.repository;

import nl.romano.moeubels.model.Order;
import nl.romano.moeubels.model.OrderCK;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends PagingAndSortingRepository<Order, OrderCK> {
    @Query(
            value = "SELECT * FROM order AS o WHERE CAST(o.actor_id AS TEXT) LIKE CAST(?1 AS TEXT)",
            nativeQuery = true
    )
    List<Order> getByActorId(UUID actorId);
}
