package nl.romano.moeubels.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name="favourite")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Jacksonized
@IdClass(FavouriteCK.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Favourite {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = " favourite_id", nullable = false)
    @JsonProperty("favouriteId")
    private UUID favouriteId;
    @Id
    @MapsId("actor_id")
    @OneToOne()
    //@PrimaryKeyJoinColumn(name = "actor_id")
    @JoinColumn(name = "actor_id")
    @JsonProperty("actor")
    private Actor actor;
    @Id
    @MapsId("products_id")
    @OneToOne()
    //@PrimaryKeyJoinColumn(name = "product_id")
    @JsonProperty("product")
    @JoinColumn(name = "product_id")
    private Product product;
    @Column(name = "created_at", nullable = false)
    @JsonProperty("createdAt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime createdAt;
    @Column(name = "modified_at", nullable = false)
    @JsonProperty("modifiedAt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime modifiedAt;
}
