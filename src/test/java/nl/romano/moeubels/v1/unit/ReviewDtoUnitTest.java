package nl.romano.moeubels.v1.unit;

import nl.romano.moeubels.contract.v1.request.create.CreateReviewRequest;
import nl.romano.moeubels.contract.v1.request.update.UpdateReviewRequest;
import nl.romano.moeubels.contract.v1.response.ReviewResponse;
import nl.romano.moeubels.model.Review;
import nl.romano.moeubels.v1.utils.ReviewObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class ReviewDtoUnitTest {
    private final ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void setup() {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    @Test
    public void whenReviewModelToReviewResponseDto_thenCorrect() {
        // Arrange
        Review review = ReviewObjectMother.genericReview();

        // Act
        ReviewResponse reviewResponse = modelMapper.map(review, ReviewResponse.class);

        // Assert
        Assertions.assertEquals(review.getReviewId(), reviewResponse.getReviewId());
        Assertions.assertEquals(review.getActor().getActorId(), reviewResponse.getActorId());
        Assertions.assertEquals(review.getProduct().getProductId(), reviewResponse.getProductId());
        Assertions.assertEquals(review.getComment(), reviewResponse.getComment());
        Assertions.assertEquals(review.getRating(), reviewResponse.getRating());
    }

    @Test
    public void whenUpdateReviewRequestToReviewModel_thenCorrect() {
        // Arrange
        UpdateReviewRequest updateReviewRequest = ReviewObjectMother.genericUpdateReviewRequest();

        // Act
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Review review = modelMapper.map(updateReviewRequest, Review.class);


        // Assert
        Assertions.assertEquals(updateReviewRequest.getActorId(), review.getActor().getActorId());
        Assertions.assertEquals(updateReviewRequest.getProductId(), review.getProduct().getProductId());
        Assertions.assertEquals(updateReviewRequest.getComment(), review.getComment());
        Assertions.assertEquals(updateReviewRequest.getRating(), review.getRating());
    }

    @Test
    public void whenCreateReviewRequestToReviewModel_thenCorrect() {
        // Arrange
        CreateReviewRequest createReviewRequest = ReviewObjectMother.genericCreateReviewRequest();

        // Act
        Review review = modelMapper.map(createReviewRequest, Review.class);

        // Assert
        Assertions.assertEquals(createReviewRequest.getActorId(), review.getActor().getActorId());
        Assertions.assertEquals(createReviewRequest.getProductId(), review.getProduct().getProductId());
        Assertions.assertEquals(createReviewRequest.getComment(), review.getComment());
        Assertions.assertEquals(createReviewRequest.getRating(), review.getRating());
    }
}
