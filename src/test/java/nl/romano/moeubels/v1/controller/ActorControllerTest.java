package nl.romano.moeubels.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.romano.moeubels.contract.v1.ApiRoutes;
import nl.romano.moeubels.controller.v1.ActorController;
import nl.romano.moeubels.controller.v1.response.ActorResponse;
import nl.romano.moeubels.dao.ActorDao;
import nl.romano.moeubels.dao.RoleDao;
import nl.romano.moeubels.model.Actor;
import nl.romano.moeubels.v1.utils.ActorObjectMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
@WebMvcTest(ActorController.class)
class ActorControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private ActorDao actorDao;
    @MockBean
    private RoleDao roleDao;
    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private MockMvc mvc;
    private Actor actor;
    private ActorResponse actorResponse;

    @BeforeEach
    void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.actor = ActorObjectMother.genericActor();
        this.actorResponse = ActorObjectMother.genericActorResponse();
    }

    @Test
    void getById() throws Exception {
        // Arrange
        Actor actor = ActorObjectMother.genericActor();
        UUID actorId = actor.getActorId();
        String requestPath = ApiRoutes.Actor.Get.replace("{id}", actorId.toString());
        ActorResponse actorResponse = ActorObjectMother.genericActorResponse();

        // Act
        given(actorDao.getById(actorId)).willReturn(Optional.of(actor));

        // Assert
        this.mvc.perform(MockMvcRequestBuilders
                .get(requestPath).secure(true))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(actorResponse)));
    }

    @Test
    void create() throws Exception{
        Actor testActor = this.actor;

        this.mvc.perform(MockMvcRequestBuilders.put("/actors")
                .secure(true).content(asJsonString(testActor)).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void update() throws Exception {
        Actor testActor = this.actor;

        this.mvc.perform(MockMvcRequestBuilders.post("/actors")
                .secure(true).content(asJsonString(testActor)).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void delete() throws Exception {
        Actor testActor = this.actor;
        UUID actorId = testActor.getActorId();
        String requestPath = String.format("/actors/%s", actorId.toString());

        this.mvc.perform(MockMvcRequestBuilders.delete(requestPath, testActor)
                .secure(true)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    private static String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}