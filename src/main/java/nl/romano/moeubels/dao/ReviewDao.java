package nl.romano.moeubels.dao;

import nl.romano.moeubels.contract.v1.request.create.CreateReviewRequest;
import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.exceptions.ReviewNotFoundException;
import nl.romano.moeubels.model.Actor;
import nl.romano.moeubels.model.Product;
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
        reviewRespository.save(review);
    }


    public void save(CreateReviewRequest reviewRequest) {
        try {
            UUID actorId = reviewRequest.getActorId();
            UUID productId = reviewRequest.getProductId();

            Actor actor = actorDao.getById(actorId).orElseThrow();
            Product product = productDao.getById(productId).orElseThrow();
            String comment = reviewRequest.getComment();
            int rating = reviewRequest.getRating();
            ZonedDateTime addedAt = ZonedDateTime.now();

            Review newReview = Review.builder()
                    .actor(actor)
                    .product(product)
                    .comment(comment)
                    .rating(rating)
                    .addedAt(addedAt)
                    .build();

            reviewRespository.save(newReview);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Review review) {
        reviewRespository.save(review);
    }

    public void delete(UUID id) throws ResourceNotFoundException {
        Review review = reviewRespository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review with id: " + id + "not found"));
        reviewRespository.delete(review);
    }
}
