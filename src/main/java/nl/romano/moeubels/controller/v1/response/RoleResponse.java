package nl.romano.moeubels.controller.v1.response;

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
public class RoleResponse {
    @NonNull
    @JsonProperty("roleId")
    private UUID roleId;

    @NonNull
    @JsonProperty("roleName")
    private String roleName;
}
