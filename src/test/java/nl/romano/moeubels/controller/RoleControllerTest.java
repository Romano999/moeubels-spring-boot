package nl.romano.moeubels.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.romano.moeubels.dao.ActorDao;
import nl.romano.moeubels.dao.RoleDao;
import nl.romano.moeubels.model.Role;
import nl.romano.moeubels.utils.ObjectMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
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
    private MockMvc mvc;
    private Role role;

    @BeforeEach
    void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.role = ObjectMother.genericRole();
    }

    @Test
    void getById() throws Exception {
        Role testRole = this.role;
        UUID roleId = testRole.getRoleId();
        String requestPath = String.format("/roles/%s", roleId.toString());

        given(roleDao.getById(roleId)).willReturn(Optional.of(testRole));

        this.mvc.perform(MockMvcRequestBuilders
                .get(requestPath).secure(true))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(testRole)));
    }

    @Test
    void create() throws Exception {
        Role testRole = this.role;

        this.mvc.perform(MockMvcRequestBuilders.put("/roles")
                .secure(true).content(asJsonString(testRole)).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void update() throws Exception {
        Role testRole = this.role;

        this.mvc.perform(MockMvcRequestBuilders.post("/roles")
                .secure(true).content(asJsonString(testRole)).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void delete() throws Exception {
        Role testRole = this.role;
        UUID roleId = testRole.getRoleId();
        String requestPath = String.format("/roles/%s", roleId.toString());

        this.mvc.perform(MockMvcRequestBuilders.delete(requestPath, testRole).secure(true))
                .andExpect(MockMvcResultMatchers.status().isOk());
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