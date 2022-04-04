package nl.romano.moeubels.controller;
import nl.romano.moeubels.dao.ActorDao;
import nl.romano.moeubels.exceptions.ActorNotFoundException;
import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.model.Actor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/actors")
public class ActorController implements CrudOperations<Actor> {
    @Autowired
    private ActorDao actorDao;

    @Override
    @GetMapping("/{uuid}")
    public ResponseEntity<?> getById(@PathVariable(value = "uuid") UUID uuid) throws ResourceNotFoundException {
        Actor actor = actorDao.getById(uuid)
                .orElseThrow(() -> new ActorNotFoundException("Actor with uuid: " + uuid + " not found"));
        return Responses.ResponseEntityOk(actor);
    }

    @Override
    @PostMapping()
    public ResponseEntity<String> create(@RequestBody Actor actor) {
        actorDao.save(actor);
        return Responses.jsonOkResponseEntity();
    }

    @Override
    @PutMapping()
    public ResponseEntity<String> update(@RequestBody Actor actor) {
        actorDao.update(actor);
        return Responses.jsonOkResponseEntity();
    }

    @Override
    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> delete(@PathVariable UUID uuid) throws ResourceNotFoundException {
        actorDao.delete(uuid);
        return Responses.jsonOkResponseEntity();
    }
}
