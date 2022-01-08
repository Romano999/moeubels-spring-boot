package nl.romano.moeubels.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name="favourite")
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Jacksonized
@IdClass(FavouriteCK.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Favourite {
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
    @Column(name = "added_at", nullable = false)
    @JsonProperty("addedAt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime addedAt;
}
