package nl.romano.moeubels.v1.utils;

import nl.romano.moeubels.controller.v1.request.create.CreateActorRequest;
import nl.romano.moeubels.controller.v1.request.update.UpdateActorRequest;
import nl.romano.moeubels.controller.v1.response.ActorResponse;
import nl.romano.moeubels.model.Actor;
import nl.romano.moeubels.model.Role;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ActorObjectMother {
    private static final ModelMapper modelMapper = new ModelMapper();

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

    public static Actor genericActorWithActorId(UUID actorId) {
        Role role = RoleObjectMother.genericRole();

        return Actor.builder()
            .actorId(actorId)
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
//            .roleId(role.getRoleId())
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

    public static Page<ActorResponse> genericActorResponsePageFromActorPage(
        Page<Actor> actors,
        Pageable pageable
    ) {
        ArrayList<ActorResponse> actorResponses = new ArrayList<>();
        actors.forEach(actor -> actorResponses.add(convertEntityToDto(actor)));
        return new PageImpl<ActorResponse>(actorResponses, pageable, actorResponses.size());
    }

    public static Page<Actor> genericActorPage() {
        List<Actor> actors = new ArrayList<>();
        int pageSize = 5;

        for (int i = 0; i < pageSize; i++) {
            Actor actor = ActorObjectMother.genericActor();
            actors.add(actor);
        }

        return new PageImpl<>(actors);
    }

    private static ActorResponse convertEntityToDto(Actor actor) {
        return modelMapper.map(actor, ActorResponse.class);
    }

}
