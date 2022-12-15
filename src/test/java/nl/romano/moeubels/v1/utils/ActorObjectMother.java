package nl.romano.moeubels.v1.utils;

import nl.romano.moeubels.contract.v1.request.create.CreateActorRequest;
import nl.romano.moeubels.contract.v1.request.update.UpdateActorRequest;
import nl.romano.moeubels.contract.v1.response.ActorResponse;
import nl.romano.moeubels.model.Actor;
import nl.romano.moeubels.model.Role;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.ZonedDateTime;
import java.util.UUID;

public class ActorObjectMother {
    public static Actor genericActor() {
        Role role = RoleObjectMother.genericRole();

        return Actor.builder()
            .actorId(UUID.randomUUID())
            .username("JohnDoe")
            .password("password")
            .firstName("John")
            .lastName("Doe")
            .role(role)
            .createdAt(ZonedDateTime.now())
            .modifiedAt(ZonedDateTime.now())
            .build();
    }

    public static UpdateActorRequest genericUpdateActorRequest() {
        Role role = RoleObjectMother.genericRole();

        return UpdateActorRequest.builder()
            .username("John Doe")
            .firstName("John")
            .lastName("Doe")
            .roleId(role.getRoleId())
            .build();
    }

    public static CreateActorRequest genericCreateActorRequest() {
        Role role = RoleObjectMother.genericRole();

        return CreateActorRequest.builder()
            .username("John Doe")
            .firstName("John")
            .lastName("Doe")
            .password(RandomStringUtils.random(8))
            .roleId(role.getRoleId())
            .build();
    }

    public static ActorResponse genericActorResponse() {
        Actor actor = genericActor();

        return ActorResponse.builder()
            .actorId(actor.getActorId())
            .firstName(actor.getFirstName())
            .lastName(actor.getLastName())
            .roleId(actor.getRole().getRoleId())
            .username(actor.getUsername())
            .build();
    }


}
