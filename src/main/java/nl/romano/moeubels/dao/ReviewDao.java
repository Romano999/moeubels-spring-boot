package nl.romano.moeubels.dao;

import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.exceptions.ReviewNotFoundException;
import nl.romano.moeubels.model.Review;
import nl.romano.moeubels.repository.ReviewRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ReviewDao {
    @Autowired
    private ReviewRespository reviewRespository;
    @Autowired
    private ActorDao actorDao;
    @Autowired
    private ProductDao productDao;

    public Optional<Review> getById(UUID id) {
        return reviewRespository.findById(id);
    }

    public Optional<List<Review>> getByProductId(UUID actorId) {
        return Optional.of(reviewRespository.findByProductId(actorId));
    }

    public void save(Review review) {
        review.setModifiedAt(ZonedDateTime.now());
        review.setCreatedAt(ZonedDateTime.now());
        reviewRespository.save(review);
    }

    public void update(Review review) {
        Review initialReview = this.getById(review.getReviewId()).orElseThrow();
        review.setModifiedAt(ZonedDateTime.now());
        review.setCreatedAt(initialReview.getCreatedAt());
        reviewRespository.save(review);
    }

    public void delete(UUID id) throws ResourceNotFoundException {
        Review review = reviewRespository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review with id: " + id + "not found"));
        reviewRespository.delete(review);
    }
}
