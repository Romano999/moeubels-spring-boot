package nl.romano.moeubels.v1.utils;

import nl.romano.moeubels.controller.v1.request.create.CreateReviewRequest;
import nl.romano.moeubels.controller.v1.request.update.UpdateReviewRequest;
import nl.romano.moeubels.controller.v1.response.ReviewResponse;
import nl.romano.moeubels.model.Actor;
import nl.romano.moeubels.model.Product;
import nl.romano.moeubels.model.Review;
import org.modelmapper.ModelMapper;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReviewObjectMother {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static Review genericReview() {
        Actor actor = ActorObjectMother.genericActor();
        Product product = ProductObjectMother.genericProduct();

        return Review.builder()
            .reviewId(UUID.randomUUID())
            .actor(actor)
            .product(product)
            .createdAt(ZonedDateTime.now())
            .modifiedAt(ZonedDateTime.now())
            .comment("This is a comment!")
            .rating(5)
            .build();
    }

    public static Review genericReviewWithProductId(UUID productId) {
        Actor actor = ActorObjectMother.genericActor();
        Product product = ProductObjectMother.genericProductWithProductId(productId);

        return Review.builder()
            .reviewId(UUID.randomUUID())
            .actor(actor)
            .product(product)
            .createdAt(ZonedDateTime.now())
            .modifiedAt(ZonedDateTime.now())
            .comment("This is a comment!")
            .rating(5)
            .build();
    }

    public static UpdateReviewRequest genericUpdateReviewRequest() {
        Actor actor = ActorObjectMother.genericActor();
        Product product = ProductObjectMother.genericProduct();

        return UpdateReviewRequest.builder()
            .actorId(actor.getActorId())
            .productId(product.getProductId())
            .comment("This is a comment!")
            .rating(5)
            .build();
    }

    public static CreateReviewRequest genericCreateReviewRequest() {
        Actor actor = ActorObjectMother.genericActor();
        Product product = ProductObjectMother.genericProduct();

        return CreateReviewRequest.builder()
            .actorId(actor.getActorId())
            .productId(product.getProductId())
            .comment("This is a comment!")
            .rating(5)
            .build();
    }

    public static List<Review> genericReviewListWithProductId(UUID productId) {
        List<Review> reviews = new ArrayList<>();
        int length = 5;

        for (int i = 0; i < length; i++) {
            Review review = ReviewObjectMother.genericReviewWithProductId(productId);
            reviews.add(review);
        }

        return reviews;
    }

    public static List<ReviewResponse> genericReviewResponseListFromReviewList(
        List<Review> reviews
    ) {
        ArrayList<ReviewResponse> reviewResponses = new ArrayList<>();
        reviews.forEach(review -> reviewResponses.add(convertEntityToDto(review)));
        return reviewResponses;
    }

    private static ReviewResponse convertEntityToDto(Review review) {
        return modelMapper.map(review, ReviewResponse.class);
    }
}
