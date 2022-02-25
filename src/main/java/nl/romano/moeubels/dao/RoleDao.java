package nl.romano.moeubels.dao;

import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.model.Role;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class RoleDao implements Dao<Role> {
    @Override
    public Optional<Role> getById(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public void save(Role role) {

    }

    @Override
    public void update(Role role) {

    }

    @Override
    public void delete(UUID uuid) throws ResourceNotFoundException {

    }
}
