package nl.romano.moeubels.controller.v1.request.create;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Jacksonized
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateReviewRequest {
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
