package nl.romano.moeubels.dao;

import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.model.Actor;
import nl.romano.moeubels.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class ActorDao implements Dao<Actor> {
    @Autowired
    private ActorRepository actorRepository;

    @Override
    public Optional<Actor> getById(UUID uuid) {
        return actorRepository.findById(uuid);
    }

    @Override
    public void save(Actor actor) {
        actorRepository.save(actor);
    }

    @Override
    public void update(Actor actor) {
        actorRepository.save(actor);
    }

    @Override
    public void delete(UUID uuid) throws ResourceNotFoundException {
        Actor actor = this.actorRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Actor with uuid: " + uuid + " not found"));
    }
}
