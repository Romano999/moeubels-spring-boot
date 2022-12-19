package nl.romano.moeubels.v1.utils;

import nl.romano.moeubels.controller.v1.request.create.CreateShoppingCartRequest;
import nl.romano.moeubels.controller.v1.request.update.UpdateShoppingCartRequest;
import nl.romano.moeubels.controller.v1.response.ShoppingCartResponse;
import nl.romano.moeubels.model.Actor;
import nl.romano.moeubels.model.Product;
import nl.romano.moeubels.model.ShoppingCart;
import org.modelmapper.ModelMapper;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShoppingCartObjectMother {
    private static final ModelMapper modelMapper = new ModelMapper();

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

    public static List<ShoppingCart> genericShoppingCartList() {
        List<ShoppingCart> items = new ArrayList<>();
        int pageSize = 5;

        for (int i = 0; i < pageSize; i++) {
            ShoppingCart newItem = ShoppingCartObjectMother.genericShoppingCart();
            items.add(newItem);
        }

        return items;
    }

    public static ShoppingCart genericShoppingCartWithActorId(UUID actorId) {
        Actor actor = ActorObjectMother.genericActorWithActorId(actorId);
        Product product = ProductObjectMother.genericProduct();

        return ShoppingCart.builder()
            .actor(actor)
            .product(product)
            .amount(3)
            .addedAt(ZonedDateTime.now())
            .build();
    }

    public static List<ShoppingCart> genericShoppingCartListWithActorId(UUID actorId) {
        List<ShoppingCart> items = new ArrayList<>();
        int length = 5;

        for (int i = 0; i < length; i++) {
            ShoppingCart newItem = ShoppingCartObjectMother.genericShoppingCartWithActorId(actorId);
            items.add(newItem);
        }

        return items;
    }

    public static List<ShoppingCartResponse> genericShoppingCartResponseListFromShoppingCartList(
        List<ShoppingCart> shoppingCart
    ) {
        ArrayList<ShoppingCartResponse> shoppingCartResponses = new ArrayList<>();
        shoppingCart.forEach(item -> shoppingCartResponses.add(convertEntityToDto(item)));
        return shoppingCartResponses;
    }

    private static ShoppingCartResponse convertEntityToDto(ShoppingCart shoppingCart) {
        return modelMapper.map(shoppingCart, ShoppingCartResponse.class);
    }
}
