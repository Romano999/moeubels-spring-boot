package nl.romano.moeubels.controller;

import nl.romano.moeubels.dao.ActorDao;
import nl.romano.moeubels.exceptions.ActorNotFoundException;
import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.model.Actor;
import nl.romano.moeubels.utils.Responses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/actors")
public class ActorController implements CrudOperations<Actor> {
    @Autowired
    private ActorDao actorDao;

    Logger logger = LoggerFactory.getLogger(ActorController.class);

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(value = "id") UUID id) throws ResourceNotFoundException {
        logger.info("Getting an Actor by id: " + id);
        Actor actor = actorDao.getById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exc = new ActorNotFoundException("Actor with id: " + id + " not found");
                    logger.error(exc.getMessage());
                    return exc;
                });
        return Responses.ResponseEntityOk(actor);
    }

    @Override
    @PostMapping()
    public ResponseEntity<String> create(@RequestBody Actor actor) {
        logger.info("Creating an Actor");
        actorDao.save(actor);
        return Responses.jsonOkResponseEntity();
    }

    @Override
    @PutMapping()
    public ResponseEntity<String> update(@RequestBody Actor actor) {
        logger.info("Updating an Actor");
        actorDao.update(actor);
        return Responses.jsonOkResponseEntity();
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) throws ResourceNotFoundException {
        logger.info("Deleting an Actor with id " + id);
        actorDao.delete(id);
        return Responses.jsonOkResponseEntity();
    }
}
