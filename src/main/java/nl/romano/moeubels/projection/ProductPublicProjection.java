package nl.romano.moeubels.projection;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.romano.moeubels.model.Actor;
import nl.romano.moeubels.model.Category;
import nl.romano.moeubels.model.Product;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.UUID;

public record ProductPublicProjection(
    @JsonProperty("category")
    Category category,
    @JsonProperty("productId")
    UUID productId,
    @JsonProperty("productName")
    String productName,
    @JsonProperty("productDescription")
    String productDescription,
    @JsonProperty("amountAvailable")
    int amountAvailable,
    @JsonProperty("price")
    BigDecimal price,
    @JsonProperty("imagePath")
    String imagePath,
    @JsonProperty("isOnSale")
    Boolean isOnSale)
{
    public static ProductPublicProjection toProductPublicProjection(Product product) {
        return new ProductPublicProjection(
                product.getCategory(),
                product.getProductId(),
                product.getProductName(),
                product.getProductDescription(),
                product.getAmountAvailable(),
                product.getPrice(),
                product.getImagePath(),
                product.getIsOnSale()
        );
    }
}

