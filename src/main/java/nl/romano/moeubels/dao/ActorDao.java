package nl.romano.moeubels.dao;

import nl.romano.moeubels.exceptions.ActorNotFoundException;
import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.model.Actor;
import nl.romano.moeubels.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ActorDao implements Dao<Actor>, UserDetailsService {
    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Actor actor = Optional.of(actorRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("Actor not found in database."));
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(actor.getRole().getRoleName()));
        authorities.add(new SimpleGrantedAuthority(actor.getActorId().toString()));

        return new User(actor.getUsername(), actor.getPassword(), authorities);
    }

    @Override
    public Optional<Actor> getById(UUID uuid) {
        return actorRepository.findById(uuid);
    }

    public Actor getByUsername(String username) {
        return actorRepository.findByUsername(username);
    }

    public Page<Actor> getAll(Pageable pageable) {
        return actorRepository.findAll(pageable);
    }

    @Override
    public void save(Actor actor) {
        actor.setPassword(passwordEncoder.encode(actor.getPassword()));
        actor.setCreatedAt(ZonedDateTime.now());
        actor.setModifiedAt(ZonedDateTime.now());
        actorRepository.save(actor);
    }

    @Override
    public void update(Actor actor) {
        Actor initialActor = getById(actor.getActorId()).orElseThrow();
        actor.setPassword(initialActor.getPassword());
        actor.setModifiedAt(ZonedDateTime.now());
        actor.setCreatedAt(initialActor.getCreatedAt());
        actorRepository.save(actor);
    }

    @Override
    public void delete(UUID uuid) throws ResourceNotFoundException {
        Actor actor = this.actorRepository.findById(uuid)
                .orElseThrow(() -> new ActorNotFoundException("Actor with uuid: " + uuid + " not found"));
        actorRepository.delete(actor);
    }
}
