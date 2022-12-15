package nl.romano.moeubels.controller.v1;

import nl.romano.moeubels.contract.v1.ApiRoutes;
import nl.romano.moeubels.controller.v1.request.create.CreateActorRequest;
import nl.romano.moeubels.controller.v1.request.update.UpdateActorRequest;
import nl.romano.moeubels.controller.v1.response.ActorResponse;
import nl.romano.moeubels.dao.ActorDao;
import nl.romano.moeubels.dao.RoleDao;
import nl.romano.moeubels.exceptions.ActorNotFoundException;
import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.model.Actor;
import nl.romano.moeubels.model.Role;
import nl.romano.moeubels.utils.Responses;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@RestController
public class ActorController {
    @Autowired
    private ActorDao actorDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private ModelMapper modelMapper;

    private final Logger logger = LoggerFactory.getLogger(ActorController.class);

    @GetMapping(ApiRoutes.Actor.Get)
    public ResponseEntity<ActorResponse> getById(@PathVariable(value = "id") UUID id) throws ResourceNotFoundException {
        logger.info("Getting an actor by actor id " + id);
        Actor actor = actorDao.getById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exc = new ActorNotFoundException("Actor with actor id " + id + " not found");
                    logger.error(exc.getMessage());
                    return exc;
                });

        ActorResponse actorResponse = convertEntityToDto(actor);
        return Responses.ResponseEntityOk(actorResponse);
    }

    @PostMapping(ApiRoutes.Actor.Create)
    public ResponseEntity<String> create(@RequestBody CreateActorRequest actorRequest) {
        Role actorRole = this.roleDao.getByName("Actor").orElseThrow();
        Actor actor = convertDtoToEntity(actorRequest);
        actor.setCreatedAt(ZonedDateTime.now());
        actor.setModifiedAt(ZonedDateTime.now());
        actor.setRole(actorRole);
        logger.info("Creating an actor");
        actorDao.save(actor);
        return Responses.jsonOkResponseEntity();
    }

    @PutMapping(ApiRoutes.Actor.Update)
    public ResponseEntity<String> update(@RequestBody UpdateActorRequest actorRequest) {
        Actor actor = convertDtoToEntity(actorRequest);
        logger.info("Updating an actor");
        actorDao.update(actor);
        return Responses.jsonOkResponseEntity();
    }

    @DeleteMapping(ApiRoutes.Actor.Delete)
    public ResponseEntity<?> delete(@PathVariable UUID id) throws ResourceNotFoundException {
        logger.info("Deleting an actor with actor id " + id);
        actorDao.delete(id);
        return Responses.jsonOkResponseEntity();
    }

    private Actor convertDtoToEntity(UpdateActorRequest updateActorRequest) {
        logger.info("Mapping update actor request to actor model");
        return modelMapper.map(updateActorRequest, Actor.class);
    }

    private Actor convertDtoToEntity(CreateActorRequest createActorRequest) {
        logger.info("Mapping create actor request to actor model");
        return modelMapper.map(createActorRequest, Actor.class);
    }

    private ActorResponse convertEntityToDto(Actor actor) {
        logger.info("Mapping actor model to actor response");
        return modelMapper.map(actor, ActorResponse.class);
    }
}
