package nl.romano.moeubels.repository;

import nl.romano.moeubels.model.Review;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface ReviewRespository extends PagingAndSortingRepository<Review, UUID> {
}
