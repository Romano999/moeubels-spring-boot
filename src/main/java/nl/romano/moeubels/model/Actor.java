package nl.romano.moeubels.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.json.JSONObject;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name= "actor")
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "actor_id", nullable = false)
    @JsonProperty("actorId")
    private UUID actorId;
    @Column(name = "username", nullable = false)
    @JsonProperty("username")
    private String username;
    @Column(name = "password", nullable = false)
    @JsonProperty("password")
    private String password;
    @Column(name = "first_name", nullable = false)
    @JsonProperty("firstName")
    private String firstName;
    @Column(name = "last_name", nullable = false)
    @JsonProperty("lastName")
    private String lastName;
    @Column(name = "created_at", nullable = false)
    @JsonProperty("createdAt")
    private ZonedDateTime createdAt;
    @Column(name = "modified_at", nullable = false)
    @JsonProperty("modifiedAt")
    private ZonedDateTime modifiedAt;
}
