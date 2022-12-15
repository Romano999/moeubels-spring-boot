package nl.romano.moeubels.v1.utils;

import nl.romano.moeubels.controller.v1.request.create.CreateProductRequest;
import nl.romano.moeubels.controller.v1.request.update.UpdateProductRequest;
import nl.romano.moeubels.model.Category;
import nl.romano.moeubels.model.Product;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

public class ProductObjectMother {
    public static Product genericProduct() {
        Category category = CategoryObjectMother.genericCategory();

        return Product.builder()
            .productId(UUID.randomUUID())
            .price(BigDecimal.TEN)
            .amountAvailable(10)
            .productName("Name")
            .productDescription("Description")
            .imagePath("ImagePath")
            .isOnSale(false)
            .category(category)
            .createdAt(ZonedDateTime.now())
            .modifiedAt(ZonedDateTime.now())
            .build();
    }

    public static UpdateProductRequest genericUpdateProductRequest() {
        Category category = CategoryObjectMother.genericCategory();

        return UpdateProductRequest.builder()
            .price(BigDecimal.TEN)
            .amountAvailable(10)
            .productName("Name")
            .productDescription("Description")
            .imagePath("Imagepath")
            .isOnSale(false)
            .categoryId(category.getCategoryId())
            .build();
    }

    public static CreateProductRequest genericCreateProductRequest() {
        Category category = CategoryObjectMother.genericCategory();

        return CreateProductRequest.builder()
            .price(BigDecimal.TEN)
            .amountAvailable(10)
            .productName("Name")
            .productDescription("Description")
            .imagePath("Imagepath")
            .isOnSale(false)
            .categoryId(category.getCategoryId())
            .build();
    }
}
