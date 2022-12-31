package nl.romano.moeubels.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.romano.moeubels.contract.v1.ApiRoutes;
import nl.romano.moeubels.controller.v1.FavouriteController;
import nl.romano.moeubels.controller.v1.request.create.CreateFavouriteRequest;
import nl.romano.moeubels.controller.v1.request.update.UpdateFavouriteRequest;
import nl.romano.moeubels.controller.v1.response.FavouriteResponse;
import nl.romano.moeubels.dao.FavouriteDao;
import nl.romano.moeubels.model.Favourite;
import nl.romano.moeubels.v1.utils.FavouriteObjectMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FavouriteController.class)
class FavouriteControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private FavouriteDao favouriteDao;
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
        Favourite favourite = FavouriteObjectMother.genericFavourite();
        FavouriteResponse favouriteResponse = modelMapper.map(favourite, FavouriteResponse.class);
        UUID favouriteId = favourite.getFavouriteId();
        String requestPath = ApiRoutes.Favourite.Get.replace("{id}", favouriteId.toString());

        given(favouriteDao.getById(favouriteId)).willReturn(Optional.of(favourite));

        // Act
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.get(requestPath).secure(true));

        // Assert
        result.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(asJsonString(favouriteResponse)));
    }

    @Test
    void getByActorId() throws Exception {
        // Arrange
        UUID actorId = UUID.randomUUID();
        List<Favourite> favourites = FavouriteObjectMother.genericFavouriteListWithActorId(actorId);
        List<FavouriteResponse> favouriteResponses =
            FavouriteObjectMother.genericFavouriteResponseListFromFavouriteList(favourites);
        String requestPath = ApiRoutes.Favourite.GetByActorId.replace("{actorId}", actorId.toString());

        given(favouriteDao.getByActorId(actorId)).willReturn(Optional.of(favourites));

        // Act
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.get(requestPath)
            .secure(true)
            .contentType("application/json"));

        // Assert
        result.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(asJsonString(favouriteResponses)));
    }

    @Test
    void create() throws Exception {
        // Arrange
        CreateFavouriteRequest favouriteRequest = FavouriteObjectMother.genericCreateFavouriteRequest();
        String requestPath = ApiRoutes.Favourite.Create;

        // Act
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.post(requestPath)
            .secure(true)
            .content(asJsonString(favouriteRequest)).contentType("application/json"));

        // Assert
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void update() throws Exception {
        // Arrange
        UUID favouriteId = UUID.randomUUID();
        UpdateFavouriteRequest favouriteRequest = FavouriteObjectMother.genericUpdateFavouriteRequest();
        String requestPath = ApiRoutes.Favourite.Update.replace("{id}", favouriteId.toString());

        // Act
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.put(requestPath)
            .secure(true)
            .content(asJsonString(favouriteRequest))
            .contentType("application/json"));

        // Assert
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void delete() throws Exception {
        // Arrange
        Favourite favourite = FavouriteObjectMother.genericFavourite();
        String requestPath = ApiRoutes.Favourite.Delete.replace("{id}", favourite.getFavouriteId().toString());

        // Act
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.delete(requestPath, favourite).secure(true));

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