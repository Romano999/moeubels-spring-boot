package nl.romano.moeubels.v1.utils;

import nl.romano.moeubels.controller.v1.request.create.CreateShoppingCartRequest;
import nl.romano.moeubels.controller.v1.request.update.UpdateShoppingCartRequest;
import nl.romano.moeubels.model.Actor;
import nl.romano.moeubels.model.Product;
import nl.romano.moeubels.model.ShoppingCart;

import java.time.ZonedDateTime;

public class ShoppingCartObjectMother {
    public static ShoppingCart genericShoppingCart() {
        Actor actor = ActorObjectMother.genericActor();
        Product product = ProductObjectMother.genericProduct();

        return ShoppingCart.builder()
            .actor(actor)
            .product(product)
            .amount(3)
            .addedAt(ZonedDateTime.now())
            .build();
    }

    public static UpdateShoppingCartRequest genericUpdateShoppingCartRequest() {
        Actor actor = ActorObjectMother.genericActor();
        Product product = ProductObjectMother.genericProduct();

        return UpdateShoppingCartRequest.builder()
            .actorId(actor.getActorId())
            .productId(product.getProductId())
            .amount(5)
            .build();
    }

    public static CreateShoppingCartRequest genericCreateShoppingCartRequest() {
        Actor actor = ActorObjectMother.genericActor();
        Product product = ProductObjectMother.genericProduct();

        return CreateShoppingCartRequest.builder()
            .actorId(actor.getActorId())
            .productId(product.getProductId())
            .amount(5)
            .build();
    }
}
