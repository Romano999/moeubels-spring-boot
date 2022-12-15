package nl.romano.moeubels.v1.utils;

import nl.romano.moeubels.controller.v1.request.create.CreateReviewRequest;
import nl.romano.moeubels.controller.v1.request.update.UpdateReviewRequest;
import nl.romano.moeubels.model.Actor;
import nl.romano.moeubels.model.Product;
import nl.romano.moeubels.model.Review;

import java.time.ZonedDateTime;
import java.util.UUID;

public class ReviewObjectMother {
    public static Review genericReview() {
        Actor actor = ActorObjectMother.genericActor();
        Product product = ProductObjectMother.genericProduct();

        return Review.builder()
            .reviewId(UUID.randomUUID())
            .actor(actor)
            .product(product)
            .addedAt(ZonedDateTime.now())
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
}
