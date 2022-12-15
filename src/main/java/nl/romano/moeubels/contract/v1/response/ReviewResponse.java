package nl.romano.moeubels.contract.v1.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Jacksonized
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ReviewResponse {
    @NonNull
    @JsonProperty("reviewId")
    private UUID reviewId;

    @NonNull
    @JsonProperty("actorId")
    private UUID actorId;

    @NonNull
    @JsonProperty("productId")
    private UUID productId;

    @NonNull
    @JsonProperty("rating")
    private Integer rating;

    @NonNull
    @JsonProperty("comment")
    private String comment;
}
