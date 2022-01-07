package nl.romano.moeubels.repository;

import nl.romano.moeubels.model.Product;
import nl.romano.moeubels.model.ShoppingCart;
import nl.romano.moeubels.model.ShoppingCartCK;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.UUID;

public interface ShoppingCartRepository extends PagingAndSortingRepository<ShoppingCart, ShoppingCartCK> {
    @Query(
            value = "SELECT * FROM shopping_cart AS s WHERE CAST(s.actor_id AS TEXT) LIKE CAST(?1 AS TEXT)",
            nativeQuery = true
    )
    List<ShoppingCart> getByActorId(UUID actorId);
}
