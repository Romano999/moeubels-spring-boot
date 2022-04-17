package nl.romano.moeubels.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.romano.moeubels.dao.ActorDao;
import nl.romano.moeubels.dao.RoleDao;
import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.exceptions.ReviewNotFoundException;
import nl.romano.moeubels.exceptions.RoleNotFoundException;
import nl.romano.moeubels.model.Actor;
import nl.romano.moeubels.model.Role;
import nl.romano.moeubels.utils.Responses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/roles")
public class RoleController implements CrudOperations<Role> {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private ActorDao actorDao;

    Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(UUID id) throws ResourceNotFoundException {
        logger.info("Getting a role with role id " + id);
        Role role = roleDao.getById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exc = new RoleNotFoundException("Role with role id " + id + " not found");
                    logger.error(exc.getMessage());
                    return exc;
                });
        return Responses.ResponseEntityOk(role);
    }

    @Override
    @PostMapping()
    public ResponseEntity<String> create(Role role) {
        logger.info("Creating a role");
        roleDao.save(role);
        return Responses.jsonOkResponseEntity();
    }

    @Override
    @PutMapping()
    public ResponseEntity<String> update(Role role) {
        logger.info("Updating a role");
        roleDao.update(role);
        return Responses.jsonOkResponseEntity();
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(UUID id) throws ResourceNotFoundException {
        logger.info("Deleting a role with id " + id);
        roleDao.delete(id);
        return Responses.jsonOkResponseEntity();
    }

    @GetMapping("token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                Actor actor = actorDao.getByUsername(username);
                String accessToken = JWT.create()
                        .withSubject(actor.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("role", actor.getRole().getRoleName())
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("accessToken", accessToken);
                tokens.put("refreshToken", refreshToken);

                response.setContentType(APPLICATION_JSON_VALUE);

                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e) {
                // TODO: Error in logger
                response.setHeader("Error", e.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
}
