package nl.romano.moeubels.controller.v1.request.create;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Jacksonized
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CreateActorRequest {
    @NonNull
    @JsonProperty("username")
    private String username;

    @NonNull
    @JsonProperty("password")
    private String password;

    @NonNull
    @JsonProperty("firstName")
    private String firstName;

    @NonNull
    @JsonProperty("lastName")
    private String lastName;
}
