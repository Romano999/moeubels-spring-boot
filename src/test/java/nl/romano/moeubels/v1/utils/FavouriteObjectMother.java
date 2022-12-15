package nl.romano.moeubels.v1.utils;

import nl.romano.moeubels.controller.v1.request.create.CreateFavouriteRequest;
import nl.romano.moeubels.controller.v1.request.update.UpdateFavouriteRequest;
import nl.romano.moeubels.model.Actor;
import nl.romano.moeubels.model.Favourite;
import nl.romano.moeubels.model.Product;

import java.time.ZonedDateTime;

public class FavouriteObjectMother {
    public static Favourite genericFavourite() {
        Actor actor = ActorObjectMother.genericActor();
        Product product = ProductObjectMother.genericProduct();

        return Favourite.builder()
            .actor(actor)
            .product(product)
            .addedAt(ZonedDateTime.now())
            .build();
    }

    public static UpdateFavouriteRequest genericUpdateFavouriteRequest() {
        Actor actor = ActorObjectMother.genericActor();
        Product product = ProductObjectMother.genericProduct();

        return UpdateFavouriteRequest.builder()
            .actorId(actor.getActorId())
            .productId(product.getProductId())
            .build();
    }

    public static CreateFavouriteRequest genericCreateFavouriteRequest() {
        Actor actor = ActorObjectMother.genericActor();
        Product product = ProductObjectMother.genericProduct();

        return CreateFavouriteRequest.builder()
            .actorId(actor.getActorId())
            .productId(product.getProductId())
            .build();
    }
}
