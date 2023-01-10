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
import nl.romano.moeubels.utils.JsonConverter;
import nl.romano.moeubels.utils.Responses;
import nl.romano.moeubels.utils.Roles;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
public class ActorController {
    @Autowired
    private ActorDao actorDao;
    @Autowired
    private RoleDao roleDao;

    private final ModelMapper modelMapper = new ModelMapper();
    private final Logger logger = LoggerFactory.getLogger(ActorController.class);

    @GetMapping(ApiRoutes.Actor.Get)
    public ResponseEntity<ActorResponse> getById(@PathVariable(value = "id") UUID id) throws ResourceNotFoundException {
        logger.info("Received following actor id '" + id + "'");
        logger.info("Getting an actor by actor id '" + id + "'");
        Actor actor = actorDao.getById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exc = new ActorNotFoundException("Actor with actor id '" + id + "' not found");
                    logger.error(exc.getMessage());
                    return exc;
                });

        logger.info("Actor with id '" + actor.getActorId() + "' found");
        ActorResponse actorResponse = convertEntityToDto(actor);
        logger.info("Returning following data: " + JsonConverter.asJsonString(actorResponse));
        return Responses.ResponseEntityOk(actorResponse);
    }

    @GetMapping(value = ApiRoutes.Actor.GetAll, params = { "page", "size" })
    public ResponseEntity<Page<ActorResponse>> getAll(@RequestParam int page, @RequestParam int size) {
        logger.info("Getting all actors on page " + page + " with size " + size);
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<Actor> actors = actorDao.getAll(pageable);

        Page<ActorResponse> actorResponses = convertEntityPageToDtoPage(actors, pageable);
        return Responses.ResponseEntityOk(actorResponses);
    }

    @PostMapping(ApiRoutes.Actor.Create)
    public ResponseEntity<String> create(@RequestBody CreateActorRequest actorRequest) {
        logger.info("Received following create actor request '" + JsonConverter.asJsonString(actorRequest) + "'");
        logger.info("Searching for role with name :'" + Roles.ACTOR.label + "'");
        Role actorRole = roleDao.getByName(Roles.ACTOR.label).orElseThrow();
        logger.info("Role with id '" + actorRole.getRoleId() + "' found");
        Actor actor = convertDtoToEntity(actorRequest);
        actor.setRole(actorRole);
        logger.info("Creating an actor");
        actorDao.save(actor);
        return Responses.jsonOkResponseEntity();
    }

    @PutMapping(ApiRoutes.Actor.Update)
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody UpdateActorRequest actorRequest) {
        logger.info("Received following update actor request '" + JsonConverter.asJsonString(actorRequest) + "'");
        Actor actor = convertDtoToEntity(actorRequest);
        actor.setActorId(id);
        logger.info("Updating an actor");
        actorDao.update(actor);
        return Responses.jsonOkResponseEntity();
    }

    @DeleteMapping(ApiRoutes.Actor.Delete)
    public ResponseEntity<?> delete(@PathVariable UUID id) throws ResourceNotFoundException {
        logger.info("Deleting an actor with actor id '" + id + "'");
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

    private Page<ActorResponse> convertEntityPageToDtoPage(Page<Actor> actors, Pageable pageable) {
        logger.info("Mapping a actor page to a actor response page");
        ArrayList<ActorResponse> actorResponses = new ArrayList<>();
        actors.forEach(actor -> actorResponses.add(convertEntityToDto(actor)));
        logger.info("Done with mapping a actor page to a actor response page");
        return new PageImpl<ActorResponse>(actorResponses, pageable, actorResponses.size());
    }
}
