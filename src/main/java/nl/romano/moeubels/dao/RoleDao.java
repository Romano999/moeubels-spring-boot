package nl.romano.moeubels.dao;

import nl.romano.moeubels.controller.RoleController;
import nl.romano.moeubels.exceptions.ActorNotFoundException;
import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.exceptions.RoleNotFoundException;
import nl.romano.moeubels.model.Role;
import nl.romano.moeubels.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class RoleDao implements Dao<Role> {
    @Autowired
    private RoleRepository roleRespository;

    Logger logger = LoggerFactory.getLogger(RoleDao.class);

    @Override
    public Optional<Role> getById(UUID id) {
        return roleRespository.findById(id);
    }

    @Override
    public void save(Role role) {
        roleRespository.save(role);
    }

    @Override
    public void update(Role role) {
        roleRespository.save(role);
    }

    @Override
    public void delete(UUID id) throws ResourceNotFoundException {
        Role role = roleRespository.findById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exc = new RoleNotFoundException("Role with role id " + id + " not found");
                    logger.error(exc.getMessage());
                    return exc;
                });
        roleRespository.delete(role);
    }
}
