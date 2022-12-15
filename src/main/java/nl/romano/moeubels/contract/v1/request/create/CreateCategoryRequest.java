package nl.romano.moeubels.contract.v1.request.create;

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
//@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateCategoryRequest {
    @NonNull
    @JsonProperty("categoryName")
    private String categoryName;

    @NonNull
    @JsonProperty("categoryDescription")
    private String categoryDescription;
}

