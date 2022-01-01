package nl.romano.moeubels.repository;

import nl.romano.moeubels.model.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface ProductRepository extends PagingAndSortingRepository<Product, UUID> {
}
