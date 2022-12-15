package nl.romano.moeubels.v1.utils;

import nl.romano.moeubels.controller.v1.request.create.CreateOrderRequest;
import nl.romano.moeubels.controller.v1.request.update.UpdateOrderRequest;
import nl.romano.moeubels.model.Actor;
import nl.romano.moeubels.model.Order;
import nl.romano.moeubels.model.Product;

public class OrderObjectMother {
    public static Order genericOrder() {
        Actor actor = ActorObjectMother.genericActor();
        Product product = ProductObjectMother.genericProduct();

        return Order.builder()
            .actor(actor)
            .product(product)
            .amount(5)
            .build();
    }

    public static UpdateOrderRequest genericUpdateOrderRequest() {
        Actor actor = ActorObjectMother.genericActor();
        Product product = ProductObjectMother.genericProduct();

        return UpdateOrderRequest.builder()
            .actorId(actor.getActorId())
            .productId(product.getProductId())
            .amount(5)
            .build();
    }

    public static CreateOrderRequest genericCreateOrderRequest() {
        Actor actor = ActorObjectMother.genericActor();
        Product product = ProductObjectMother.genericProduct();

        return CreateOrderRequest.builder()
            .actorId(actor.getActorId())
            .productId(product.getProductId())
            .amount(5)
            .build();
    }
}
