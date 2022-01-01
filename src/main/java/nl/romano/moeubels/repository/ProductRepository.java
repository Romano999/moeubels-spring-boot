package nl.romano.moeubels.repository;

import nl.romano.moeubels.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface ProductRepository extends PagingAndSortingRepository<Product, UUID> {
    @Query(
            value = "SELECT * FROM product AS p WHERE LOWER(p.product_name) LIKE LOWER(concat('%', ?1, '%'));",
            nativeQuery = true,
            countQuery = "SELECT count(*) FROM product"
    )
    Page<Product> getByName(String desc, Pageable pageable);
}
