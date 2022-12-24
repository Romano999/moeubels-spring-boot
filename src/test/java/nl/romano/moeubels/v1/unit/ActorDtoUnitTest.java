package nl.romano.moeubels.v1.unit;

import nl.romano.moeubels.controller.v1.request.create.CreateActorRequest;
import nl.romano.moeubels.controller.v1.request.update.UpdateActorRequest;
import nl.romano.moeubels.controller.v1.response.ActorResponse;
import nl.romano.moeubels.model.Actor;
import nl.romano.moeubels.v1.utils.ActorObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

public class ActorDtoUnitTest {
    private final ModelMapper modelMapper = new ModelMapper();

    @Test
    public void whenActorModelToActorResponseDto_thenCorrect() {
        // Arrange
        Actor actor = ActorObjectMother.genericActor();

        // Act
        ActorResponse actorResponse = modelMapper.map(actor, ActorResponse.class);

        // Assert
        Assertions.assertEquals(actor.getActorId(), actorResponse.getActorId());
        Assertions.assertEquals(actor.getUsername(), actorResponse.getUsername());
        Assertions.assertEquals(actor.getFirstName(), actorResponse.getFirstName());
        Assertions.assertEquals(actor.getLastName(), actorResponse.getLastName());
        Assertions.assertEquals(actor.getRole().getRoleId(), actorResponse.getRoleId());
    }

    @Test
    public void whenUpdateActorDtoToActorModel_thenCorrect() {
        // Arrange
        UpdateActorRequest updateActorRequest = ActorObjectMother.genericUpdateActorRequest();

        // Act
        Actor actor = modelMapper.map(updateActorRequest, Actor.class);

        // Assert
        Assertions.assertEquals(updateActorRequest.getUsername(), actor.getUsername());
        Assertions.assertEquals(updateActorRequest.getFirstName(), actor.getFirstName());
        Assertions.assertEquals(updateActorRequest.getLastName(), actor.getLastName());
        Assertions.assertEquals(updateActorRequest.getRoleId(), actor.getRole().getRoleId());
    }

    @Test
    public void whenCreateActorDtoToActorModel_thenCorrect() {
        // Arrange
        CreateActorRequest createActorRequest = ActorObjectMother.genericCreateActorRequest();

        // Act
        Actor actor = modelMapper.map(createActorRequest, Actor.class);

        // Assert
        Assertions.assertEquals(createActorRequest.getUsername(), actor.getUsername());
        Assertions.assertEquals(createActorRequest.getFirstName(), actor.getFirstName());
        Assertions.assertEquals(createActorRequest.getLastName(), actor.getLastName());
        Assertions.assertEquals(createActorRequest.getPassword(), actor.getPassword());
//        Assertions.assertEquals(createActorRequest.getRoleId(), actor.getRole().getRoleId());
    }



}
