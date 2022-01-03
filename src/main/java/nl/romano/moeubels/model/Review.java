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
import java.util.UUID;

@Entity
@Table(name= "product")
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class Review {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id")
    @Column(name = "actor_id", nullable = false)
    @JsonProperty("actorId")
    private UUID actorId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @Column(name = "product_id", nullable = false)
    @JsonProperty("productId")
    private UUID productId;
    @Column(name = "created_at", nullable = false)
    @JsonProperty("createdAt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime createdAt;
}
