package nl.romano.moeubels.contract.v1.request.update;

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
public class UpdateActorRequest {
    @NonNull
    @JsonProperty("username")
    private final String username;

    @NonNull
    @JsonProperty("firstName")
    private String firstName;

    @NonNull
    @JsonProperty("lastName")
    private String lastName;

    @NonNull
    @JsonProperty("roleId")
    private UUID roleId;
}
