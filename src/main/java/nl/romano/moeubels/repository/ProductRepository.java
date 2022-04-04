package nl.romano.moeubels.repository;

import nl.romano.moeubels.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends PagingAndSortingRepository<Product, UUID> {
    Page<Product> findAllByProductNameIgnoreCaseContaining(String productName, Pageable pageable);
}
