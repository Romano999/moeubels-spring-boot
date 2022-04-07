package nl.romano.moeubels.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.romano.moeubels.dao.ActorDao;
import nl.romano.moeubels.model.Actor;
import nl.romano.moeubels.utils.ObjectMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
        this.actor = ObjectMother.genericActor();
    }

    @Test
    void getById() throws Exception {
        Actor testActor = this.actor;
        UUID actorId = testActor.getActorId();
        //String actorJsonString = new JSONObject(testActor).toString();
        String requestPath = String.format("/actors/%s", actorId.toString());

        given(actorDao.getById(actorId)).willReturn(Optional.of(testActor));

        this.mvc.perform(MockMvcRequestBuilders
                .get(requestPath).secure(true))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(testActor)));
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
            throw new RuntimeException(e);
        }
    }
}