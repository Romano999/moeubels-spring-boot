package nl.romano.moeubels.controller.v1.request.create;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Jacksonized
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateProductRequest {
    @NonNull
    @JsonProperty("categoryName")
    private String categoryName;

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
}