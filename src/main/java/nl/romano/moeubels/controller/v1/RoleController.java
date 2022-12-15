package nl.romano.moeubels.controller.v1;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.romano.moeubels.contract.v1.ApiRoutes;
import nl.romano.moeubels.controller.v1.request.create.CreateRoleRequest;
import nl.romano.moeubels.controller.v1.request.update.UpdateRoleRequest;
import nl.romano.moeubels.controller.v1.response.RoleResponse;
import nl.romano.moeubels.dao.ActorDao;
import nl.romano.moeubels.dao.RoleDao;
import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.exceptions.RoleNotFoundException;
import nl.romano.moeubels.model.Actor;
import nl.romano.moeubels.model.Role;
import nl.romano.moeubels.utils.ExpiryDate;
import nl.romano.moeubels.utils.Responses;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class RoleController {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private ActorDao actorDao;
    @Autowired
    private ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(RoleController.class);

    @GetMapping(ApiRoutes.Role.Get)
    public ResponseEntity<RoleResponse> getById(@PathVariable UUID id) throws ResourceNotFoundException {
        logger.info("Getting a role with role id " + id);
        Role role = roleDao.getById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exc = new RoleNotFoundException("Role with role id " + id + " not found");
                    logger.error(exc.getMessage());
                    return exc;
                });

        RoleResponse roleResponse = convertEntityToDto(role);
        return Responses.ResponseEntityOk(roleResponse);
    }

    @PostMapping(ApiRoutes.Role.Create)
    public ResponseEntity<String> create(@RequestBody CreateRoleRequest roleRequest) {
        Role role = convertDtoToEntity(roleRequest);
        logger.info("Creating a role");
        roleDao.save(role);
        return Responses.jsonOkResponseEntity();
    }

    @PutMapping(ApiRoutes.Role.Update)
    public ResponseEntity<String> update(@RequestBody UpdateRoleRequest roleRequest) {
        Role role = convertDtoToEntity(roleRequest);
        logger.info("Updating a role");
        roleDao.update(role);
        return Responses.jsonOkResponseEntity();
    }

    @DeleteMapping(ApiRoutes.Role.Delete)
    public ResponseEntity<?> delete(@PathVariable UUID id) throws ResourceNotFoundException {
        logger.info("Deleting a role with id " + id);
        roleDao.delete(id);
        return Responses.jsonOkResponseEntity();
    }

    @PostMapping(ApiRoutes.Role.Refresh)
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        logger.info("Auth header" + request.getHeader(AUTHORIZATION));
        logger.info("Info" + request.getHeaderNames());
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
                        .withExpiresAt(ExpiryDate.getAccessTokenDate())
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("role", Stream.of(actor.getRole()).map(Role::getRoleName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("accessToken", accessToken);
                tokens.put("refreshToken", refreshToken);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e) {
                logger.info("Error occured: " + e.getMessage());
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
        logger.info(String.join("", response.getHeaderNames()));
    }

    private Role convertDtoToEntity(UpdateRoleRequest updateRoleRequest) {
        logger.info("Mapping update role request to role model");
        return modelMapper.map(updateRoleRequest, Role.class);
    }

    private Role convertDtoToEntity(CreateRoleRequest createRoleRequest) {
        logger.info("Mapping create role request to role model");
        return modelMapper.map(createRoleRequest, Role.class);
    }

    private RoleResponse convertEntityToDto(Role role) {
        logger.info("Mapping role model to role response");
        return modelMapper.map(role, RoleResponse.class);
    }
}
