package nl.romano.moeubels.projection;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.romano.moeubels.model.Actor;
import org.springframework.stereotype.Component;

import java.util.UUID;

public record ActorPublicProjection(
        @JsonProperty("username")
        String getUsername
) {
    public static ActorPublicProjection toActorPublicProjection(Actor actor) {
        return new ActorPublicProjection(
                actor.getUsername()
        );
    }
}
