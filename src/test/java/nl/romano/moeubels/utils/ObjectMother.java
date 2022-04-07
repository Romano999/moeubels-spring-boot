package nl.romano.moeubels.utils;

import nl.romano.moeubels.model.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

public class ObjectMother {
    public static Actor genericActor() {
        return Actor.builder()
                .actorId(UUID.randomUUID())
                .username("JohnDoe")
                .password("password")
                .firstName("John")
                .lastName("Doe")
                .createdAt(ZonedDateTime.now())
                .modifiedAt(ZonedDateTime.now())
                .build();
    }

    public static Category genericCategory() {
        return Category.builder()
                .categoryId(UUID.randomUUID())
                .categoryName("Name")
                .categoryDescription("Description")
                .createdAt(ZonedDateTime.now())
                .modifiedAt(ZonedDateTime.now())
                .build();
    }

    public static Favourite genericFavourite() {
        return Favourite.builder()
                .actor(Actor.builder().actorId(UUID.randomUUID()).build())
                .product(Product.builder().productId(UUID.randomUUID()).build())
                .addedAt(ZonedDateTime.now())
                .build();
    }

    public static Product genericProduct() {
        return Product.builder()
                .productId(UUID.randomUUID())
                .price(BigDecimal.TEN)
                .productName("Name")
                .productDescription("Description")
                .imagePath("ImagePath")
                .isOnSale(false)
                .createdAt(ZonedDateTime.now())
                .modifiedAt(ZonedDateTime.now())
                .build();
    }

    public static Review genericReview() {
        return Review.builder()
                .reviewId(UUID.randomUUID())
                .addedAt(ZonedDateTime.now())
                .rating(5)
                .build();
    }

    public static ShoppingCart genericShoppingCart() {
        return ShoppingCart.builder()
                .actor(Actor.builder().actorId(UUID.randomUUID()).build())
                .product(Product.builder().productId(UUID.randomUUID()).build())
                .amount(3)
                .addedAt(ZonedDateTime.now())
                .build();
    }
}
