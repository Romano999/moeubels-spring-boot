package nl.romano.moeubels.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.romano.moeubels.contract.v1.ApiRoutes;
import nl.romano.moeubels.controller.v1.RoleController;
import nl.romano.moeubels.controller.v1.request.create.CreateRoleRequest;
import nl.romano.moeubels.controller.v1.request.update.UpdateRoleRequest;
import nl.romano.moeubels.controller.v1.response.RoleResponse;
import nl.romano.moeubels.dao.ActorDao;
import nl.romano.moeubels.dao.RoleDao;
import nl.romano.moeubels.model.Role;
import nl.romano.moeubels.v1.utils.RoleObjectMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RoleController.class)
class RoleControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private RoleDao roleDao;
    @MockBean
    private ActorDao actorDao;
    @Autowired
    private MockMvc mvc;

    private final ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getById() throws Exception {
        // Arrange
        Role role = RoleObjectMother.genericRole();
        RoleResponse roleResponse = modelMapper.map(role, RoleResponse.class);
        UUID roleId = role.getRoleId();
        String requestPath = ApiRoutes.Role.Get.replace("{id}", roleId.toString());

        given(roleDao.getById(roleId)).willReturn(Optional.of(role));

        // Act
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.get(requestPath).secure(true));

        // Assert
        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(roleResponse)));
    }

    @Test
    void create() throws Exception {
        // Arrange
        CreateRoleRequest roleRequest = RoleObjectMother.genericCreateRoleRequest();
        String requestPath = ApiRoutes.Role.Create;

        // Act
        ResultActions result =  this.mvc.perform(MockMvcRequestBuilders.post(requestPath)
            .secure(true).content(asJsonString(roleRequest)).contentType("application/json"));

        // Assert
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void update() throws Exception {
        // Arrange
        UUID roleId = UUID.randomUUID();
        UpdateRoleRequest roleRequest = RoleObjectMother.genericUpdateRoleRequest();
        String requestPath = ApiRoutes.Role.Update.replace("{id}", roleId.toString());

        // Act
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.put(requestPath)
            .secure(true)
            .content(asJsonString(roleRequest))
            .contentType("application/json"));

        // Assert
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void delete() throws Exception {
        // Arrange
        Role role = RoleObjectMother.genericRole();
        UUID roleId = role.getRoleId();
        String requestPath = ApiRoutes.Role.Delete.replace("{id}", roleId.toString());

        // Act
        ResultActions result= this.mvc.perform(MockMvcRequestBuilders.delete(requestPath, role).secure(true));

        // Assert
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    private static String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}