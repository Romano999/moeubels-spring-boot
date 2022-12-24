package nl.romano.moeubels.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.romano.moeubels.contract.v1.ApiRoutes;
import nl.romano.moeubels.controller.v1.ActorController;
import nl.romano.moeubels.controller.v1.request.create.CreateActorRequest;
import nl.romano.moeubels.controller.v1.request.update.UpdateActorRequest;
import nl.romano.moeubels.controller.v1.response.ActorResponse;
import nl.romano.moeubels.dao.ActorDao;
import nl.romano.moeubels.dao.RoleDao;
import nl.romano.moeubels.model.Actor;
import nl.romano.moeubels.model.Role;
import nl.romano.moeubels.v1.utils.ActorObjectMother;
import nl.romano.moeubels.v1.utils.RoleObjectMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
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
    @Autowired
    private MockMvc mvc;

    private final ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getById() throws Exception {
        // Arrange
        Actor actor = ActorObjectMother.genericActor();
        ActorResponse actorResponse = modelMapper.map(actor, ActorResponse.class);
        UUID actorId = actor.getActorId();
        String requestPath = ApiRoutes.Actor.Get.replace("{id}", actorId.toString());

        given(actorDao.getById(actorId)).willReturn(Optional.of(actor));

        // Act
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.get(requestPath).secure(true));

        // Assert
        result.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(asJsonString(actorResponse)));
    }

    @Test
    void getAll() throws Exception {
        // Arrange
        Page<Actor> actorPage = ActorObjectMother.genericActorPage();
        Pageable pageable = Pageable.ofSize(5).withPage(0);
        Page<ActorResponse> actorResponsePage =
            ActorObjectMother.genericActorResponsePageFromActorPage(actorPage, pageable);
        String requestPath = ApiRoutes.Actor.GetAll;
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("page", String.valueOf(pageable.getPageNumber()));
        requestParams.add("size", String.valueOf(pageable.getPageSize()));

        given(actorDao.getAll(pageable)).willReturn(actorPage);

        // Act
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.get(requestPath).secure(true).params(requestParams));

        // Assert
        result.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(asJsonString(actorResponsePage)));
    }

    @Test
    void create() throws Exception{
        // Arrange
        CreateActorRequest actorRequest = ActorObjectMother.genericCreateActorRequest();
        Role role = RoleObjectMother.genericRole();
        String requestPath = ApiRoutes.Actor.Create;

//        given(roleDao.getById(actorRequest.getRoleId())).willReturn(Optional.of(role));

        // Act
        ResultActions result =  this.mvc.perform(MockMvcRequestBuilders.post(requestPath)
            .secure(true).content(asJsonString(actorRequest)).contentType("application/json"));

        // Assert
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void update() throws Exception {
        // Arrange
        UUID actorId = UUID.randomUUID();
        UpdateActorRequest actorRequest = ActorObjectMother.genericUpdateActorRequest();
        String requestPath = ApiRoutes.Actor.Update.replace("{id}", actorId.toString());

        // Act
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.put(requestPath)
            .secure(true).content(asJsonString(actorRequest)).contentType("application/json"));

        // Assert
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void delete() throws Exception {
        // Arrange
        Actor actor = ActorObjectMother.genericActor();
        String requestPath = ApiRoutes.Actor.Delete.replace("{id}", actor.getActorId().toString());

        // Act
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.delete(requestPath, actor).secure(true));

        // Assert
        result.andExpect(MockMvcResultMatchers.status().isOk());
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