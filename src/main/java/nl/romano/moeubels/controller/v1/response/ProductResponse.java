package nl.romano.moeubels.controller.v1.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Jacksonized
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductResponse {
    @NonNull
    @JsonProperty("category")
    private CategoryResponse category;

    @NonNull
    @JsonProperty("productId")
    private UUID productId;

    @NonNull
    @JsonProperty("productName")
    private String productName;

    @NonNull
    @JsonProperty("productDescription")
    private String productDescription;

    @NonNull
    @JsonProperty("amountAvailable")
    private Integer amountAvailable;

    @NonNull
    @JsonProperty("price")
    private BigDecimal price;

    @NonNull
    @JsonProperty("imagePath")
    private String imagePath;

    @NonNull
    @JsonProperty("isOnSale")
    private Boolean isOnSale;
}
