package nl.romano.moeubels.dao;

import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.exceptions.ReviewNotFoundException;
import nl.romano.moeubels.model.Review;
import nl.romano.moeubels.repository.ReviewRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class ReviewDao implements Dao<Review> {
    @Autowired
    private ReviewRespository reviewRespository;

    @Override
    public Optional<Review> getById(UUID uuid) {
        return reviewRespository.findById(uuid);
    }

    @Override
    public void save(Review review) {
        reviewRespository.save(review);
    }

    @Override
    public void update(Review review) {
        reviewRespository.save(review);
    }

    @Override
    public void delete(UUID uuid) throws ResourceNotFoundException {
        Review review = reviewRespository.findById(uuid)
                .orElseThrow(() -> new ReviewNotFoundException("Review with id: " + uuid + "not found"));
        reviewRespository.delete(review);
    }
}
