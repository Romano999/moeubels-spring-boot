package nl.romano.moeubels.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.romano.moeubels.controller.v1.FavouriteController;
import nl.romano.moeubels.dao.FavouriteDao;
import nl.romano.moeubels.model.Favourite;
import nl.romano.moeubels.model.FavouriteCK;
import nl.romano.moeubels.v1.utils.FavouriteObjectMother;
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
    private MockMvc mvc;
    private Favourite favourite;

    @BeforeEach
    void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.favourite = FavouriteObjectMother.genericFavourite();
    }

    @Test
    void getByActorId() throws Exception {
        Favourite testFavourite = this.favourite;

        UUID actorId = testFavourite.getActor().getActorId();

        String requestPath = String.format("/favourites/%s", actorId.toString());

        given(favouriteDao.getByActorId(actorId)).willReturn(Optional.of(List.of(testFavourite)));

        this.mvc.perform(MockMvcRequestBuilders
                        .get(requestPath).secure(true).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(List.of(testFavourite))));
    }

    @Test
    void create() throws Exception {
        Favourite testFavourite = this.favourite;
        FavouriteCK ck = FavouriteCK.builder()
                .actor(testFavourite.getActor())
                .product(testFavourite.getProduct())
                .build();

        this.mvc.perform(MockMvcRequestBuilders.put("/favourites")
                        .secure(true).content(asJsonString(ck)).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void update() throws Exception {
        Favourite testFavourite = this.favourite;

        this.mvc.perform(MockMvcRequestBuilders.post("/favourites")
                        .secure(true).content(asJsonString(testFavourite)).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void delete() throws Exception {
        Favourite testFavourite = this.favourite;
        FavouriteCK ck = FavouriteCK.builder()
                .actor(testFavourite.getActor())
                .product(testFavourite.getProduct())
                .build();

        this.mvc.perform(MockMvcRequestBuilders.delete("/favourites")
                        .secure(true).content(asJsonString(ck)).contentType("application/json"))
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