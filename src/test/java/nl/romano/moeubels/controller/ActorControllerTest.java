package nl.romano.moeubels.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.romano.moeubels.dao.ActorDao;
import nl.romano.moeubels.model.Actor;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.ZonedDateTime;
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

    private MockMvc mvc;

    private Actor actor;

    @BeforeEach
    void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        /* All timestamps in this model are set to null because the time is saved
        differently in the java code and in a json string which causes the test to fail.*/
        this.actor = Actor.builder()
                .actorId(UUID.randomUUID())
                .username("JohnDoe")
                .password("password")
                .firstName("John")
                .lastName("Doe")
                .createdAt(null)
                .modifiedAt(null)
                .build();
    }

    @Test
    void getById() throws Exception {
        Actor testActor = this.actor;
        UUID actorId = testActor.getActorId();
        String actorJsonString = new JSONObject(testActor).toString();
        String requestPath = String.format("/actors/%s", actorId.toString());

        given(actorDao.getById(actorId)).willReturn(Optional.of(testActor));

        this.mvc.perform(MockMvcRequestBuilders
                .get(requestPath).secure(true))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(actorJsonString));
    }

    @Test
    void create() throws Exception{
        Actor testActor = this.actor;
        testActor.setCreatedAt(ZonedDateTime.now());
        testActor.setModifiedAt(ZonedDateTime.now());

        this.mvc.perform(MockMvcRequestBuilders.put("/actors").secure(true).content(asJsonString(testActor)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void update() throws Exception {
        Actor testActor = this.actor;

        this.mvc.perform(MockMvcRequestBuilders.post("/actors").secure(true))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void delete() throws Exception {
        Actor testActor = this.actor;
        UUID actorId = testActor.getActorId();
        String requestPath = String.format("/actors/%s", actorId.toString());

        this.mvc.perform(MockMvcRequestBuilders.delete(requestPath, testActor).secure(true))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}