package nl.romano.moeubels.controller.v1.request.create;

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
public class CreateFavouriteRequest {
    @NonNull
    @JsonProperty("actorId")
    private UUID actorId;

    @NonNull
    @JsonProperty("productId")
    private UUID productId;
}
