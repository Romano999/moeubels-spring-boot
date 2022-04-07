package nl.romano.moeubels.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.romano.moeubels.dao.FavouriteDao;
import nl.romano.moeubels.dao.ShoppingCartDao;
import nl.romano.moeubels.model.*;
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

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ShoppingCartController.class)
class ShoppingCartControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private ShoppingCartDao shoppingCartDao;
    private MockMvc mvc;
    private ShoppingCart shoppingCart;

    @BeforeEach
    void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.shoppingCart = ObjectMother.genericShoppingCart();
    }

    @Test
    void getByActorId() throws Exception {
        ShoppingCart testShoppingCart = this.shoppingCart;

        UUID actorId = testShoppingCart.getActor().getActorId();

        String requestPath = String.format("/shoppingcart/%s", actorId.toString());

        given(shoppingCartDao.getByActorId(actorId)).willReturn(Optional.of(List.of(testShoppingCart)));

        this.mvc.perform(MockMvcRequestBuilders
                        .get(requestPath).secure(true).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(Optional.of(List.of(testShoppingCart)))));
    }

    @Test
    void create() throws Exception {
        ShoppingCart testShoppingCart = this.shoppingCart;
        ShoppingCartRequest scr = ShoppingCartRequest.builder()
                .actorId(testShoppingCart.getActor().getActorId())
                .productId(testShoppingCart.getProduct().getProductId())
                .build();

        this.mvc.perform(MockMvcRequestBuilders.put("/shoppingcart")
                        .secure(true).content(asJsonString(scr)).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void update() throws Exception {
        ShoppingCart testShoppingCart = this.shoppingCart;

        this.mvc.perform(MockMvcRequestBuilders.post("/shoppingcart")
                        .secure(true).content(asJsonString(testShoppingCart)).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void delete() throws Exception {
        ShoppingCart testShoppingCart = this.shoppingCart;
        ShoppingCartCK ck = ShoppingCartCK.builder()
                //.actor(testShoppingCart.getActor())
                .product(testShoppingCart.getProduct().getProductId())
                .build();

        this.mvc.perform(MockMvcRequestBuilders.delete("/shoppingcart")
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