package nl.romano.moeubels.controller;

import nl.romano.moeubels.dao.ActorDao;
import nl.romano.moeubels.dao.RoleDao;
import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.exceptions.RoleNotFoundException;
import nl.romano.moeubels.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/roles")
public class RoleController implements CrudOperations<Role> {
    @Autowired
    private RoleDao roleDao;

    @Override
    public ResponseEntity<?> getById(UUID uuid) throws ResourceNotFoundException {
        Role role = roleDao.getById(uuid)
                .orElseThrow(() -> new RoleNotFoundException(""));
        return Responses.ResponseEntityOk(role);
    }

    @Override
    public ResponseEntity<String> create(Role role) {
        roleDao.save(role);
        return Responses.jsonOkResponseEntity();
    }

    @Override
    public ResponseEntity<String> update(Role role) {
        roleDao.update(role);
        return Responses.jsonOkResponseEntity();
    }

    @Override
    public ResponseEntity<?> delete(UUID uuid) throws ResourceNotFoundException {
        roleDao.delete(uuid);
        return Responses.jsonOkResponseEntity();
    }
}
