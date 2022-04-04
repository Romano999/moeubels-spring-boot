package nl.romano.moeubels.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name= "product")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Jacksonized
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    @OneToOne()
    @JoinColumn(name = "category_id")
    @JsonProperty("category")
    private Category category;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id", nullable = false)
    @JsonProperty("productId")
    private UUID productId;
    @Column(name = "product_name", nullable = false, unique = true)
    @JsonProperty("productName")
    private String productName;
    @Column(name = "product_description", nullable = false)
    @JsonProperty("productDescription")
    private String productDescription;
    @Column(name = "amount_available", nullable = false)
    @JsonProperty("amountAvailable")
    private int amountAvailable;
    @Column(name = "price", nullable = false)
    @JsonProperty("price")
    private BigDecimal price;
    @Column(name = "image_path")
    @JsonProperty("imagePath")
    private String imagePath;
    @Column(name = "is_on_sale")
    @JsonProperty("isOnSale")
    private Boolean isOnSale;
    @Column(name = "created_at", nullable = false)
    @JsonProperty("createdAt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime createdAt;
    @Column(name = "modified_at", nullable = false)
    @JsonProperty("modifiedAt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime modifiedAt;
}
