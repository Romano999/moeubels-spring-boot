package nl.romano.moeubels.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name="actor")
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Jacksonized
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@JsonIgnoreProperties(ignoreUnknown = true)
public class Actor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "actor_id", nullable = false)
    @JsonProperty("actorId")
    //@OneToOne(mappedBy = "actor_id", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime createdAt;
    @Column(name = "modified_at", nullable = false)
    @JsonProperty("modifiedAt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime modifiedAt;
}
