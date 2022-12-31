package nl.romano.moeubels.v1.utils;

import nl.romano.moeubels.controller.v1.request.create.CreateProductRequest;
import nl.romano.moeubels.controller.v1.request.update.UpdateProductRequest;
import nl.romano.moeubels.controller.v1.response.ProductResponse;
import nl.romano.moeubels.model.Category;
import nl.romano.moeubels.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductObjectMother {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static Product genericProduct() {
        Category category = CategoryObjectMother.genericCategory();

        return Product.builder()
            .productId(UUID.randomUUID())
            .price(BigDecimal.TEN)
            .amountAvailable(10)
            .productName("Name")
            .productDescription("Description")
            .imagePath("ImagePath")
            .category(category)
            .createdAt(ZonedDateTime.now())
            .modifiedAt(ZonedDateTime.now())
            .build();
    }

    public static Product genericProductWithProductId(UUID productId) {
        Category category = CategoryObjectMother.genericCategory();

        return Product.builder()
            .productId(productId)
            .price(BigDecimal.TEN)
            .amountAvailable(10)
            .productName("Name")
            .productDescription("Description")
            .imagePath("ImagePath")
            .category(category)
            .createdAt(ZonedDateTime.now())
            .modifiedAt(ZonedDateTime.now())
            .build();
    }

    public static Product genericProductWithName(String name) {
        Category category = CategoryObjectMother.genericCategory();

        return Product.builder()
            .productId(UUID.randomUUID())
            .price(BigDecimal.TEN)
            .amountAvailable(10)
            .productName(name)
            .productDescription("Description")
            .imagePath("ImagePath")
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
//            .isOnSale(false)
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

    public static Page<Product> genericProductPage() {
        List<Product> products = new ArrayList<>();
        int pageSize = 5;

        for (int i = 0; i < pageSize; i++) {
            Product newProduct = ProductObjectMother.genericProduct();
            products.add(newProduct);
        }

        return new PageImpl<>(products);
    }

    public static Page<Product> genericProductPageWithName(String name) {
        List<Product> products = new ArrayList<>();
        int pageSize = 5;

        for (int i = 0; i < pageSize; i++) {
            Product newProduct = ProductObjectMother.genericProductWithName(name);
            products.add(newProduct);
        }

        return new PageImpl<>(products);
    }

    public static Page<ProductResponse> genericProductResponsePageFromProductPage(
        Page<Product> products,
        Pageable pageable
    ) {
        ArrayList<ProductResponse> productResponses = new ArrayList<>();
        products.forEach(product -> productResponses.add(convertEntityToDto(product)));
        return new PageImpl<ProductResponse>(productResponses, pageable, productResponses.size());
    }

    private static ProductResponse convertEntityToDto(Product product) {
        return modelMapper.map(product, ProductResponse.class);
    }
}
