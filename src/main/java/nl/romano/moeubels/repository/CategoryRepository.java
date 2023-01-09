package nl.romano.moeubels.repository;

import nl.romano.moeubels.model.Category;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface CategoryRepository extends PagingAndSortingRepository<Category, UUID> {
    Category findByCategoryNameIgnoreCaseContaining(String categoryName);
}
