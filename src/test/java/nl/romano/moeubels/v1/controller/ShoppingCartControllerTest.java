package nl.romano.moeubels.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.romano.moeubels.contract.v1.ApiRoutes;
import nl.romano.moeubels.controller.v1.ShoppingCartController;
import nl.romano.moeubels.controller.v1.request.create.CreateShoppingCartRequest;
import nl.romano.moeubels.controller.v1.request.update.UpdateShoppingCartRequest;
import nl.romano.moeubels.controller.v1.response.ShoppingCartResponse;
import nl.romano.moeubels.dao.ActorDao;
import nl.romano.moeubels.dao.ProductDao;
import nl.romano.moeubels.dao.ShoppingCartDao;
import nl.romano.moeubels.model.Actor;
import nl.romano.moeubels.model.Product;
import nl.romano.moeubels.model.ShoppingCart;
import nl.romano.moeubels.model.ShoppingCartCK;
import nl.romano.moeubels.v1.utils.ActorObjectMother;
import nl.romano.moeubels.v1.utils.ProductObjectMother;
import nl.romano.moeubels.v1.utils.ShoppingCartObjectMother;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ShoppingCartController.class)
class ShoppingCartControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private ShoppingCartDao shoppingCartDao;
    @MockBean
    private ActorDao actorDao;
    @MockBean
    private ProductDao productDao;

    @Autowired
    private MockMvc mvc;

    private final ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getByActorId() throws Exception {
        // Arrange
        UUID actorId = UUID.randomUUID();
        List<ShoppingCart> shoppingCart = ShoppingCartObjectMother.genericShoppingCartListWithActorId(actorId);
        List<ShoppingCartResponse> shoppingCartResponse =
            ShoppingCartObjectMother.genericShoppingCartResponseListFromShoppingCartList(shoppingCart);
        String requestPath = ApiRoutes.ShoppingCart.GetByActorId.replace("{actorId}", actorId.toString());

        given(shoppingCartDao.getByActorId(actorId)).willReturn(Optional.of(shoppingCart));

        // Act
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.get(requestPath)
            .secure(true)
            .contentType("application/json"));

        // Assert
        result.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(asJsonString(shoppingCartResponse)));
    }

    @Test
    void create() throws Exception {
        // Arrange
        CreateShoppingCartRequest shoppingCartRequest = ShoppingCartObjectMother.genericCreateShoppingCartRequest();
        Actor actor = ActorObjectMother.genericActor();
        Product product = ProductObjectMother.genericProduct();

        String requestPath = ApiRoutes.ShoppingCart.Create;

        given(actorDao.getById(shoppingCartRequest.getActorId())).willReturn(Optional.of(actor));
        given(productDao.getById(shoppingCartRequest.getProductId())).willReturn(Optional.of(product));

        // Act
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.post(requestPath)
            .secure(true)
            .content(asJsonString(shoppingCartRequest))
            .contentType("application/json"));

        // Assert
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void update() throws Exception {
        // Arrange
        UpdateShoppingCartRequest shoppingCartRequest = ShoppingCartObjectMother.genericUpdateShoppingCartRequest();
        String requestPath = ApiRoutes.ShoppingCart.Update;

        // Act
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.put(requestPath)
            .secure(true)
            .content(asJsonString(shoppingCartRequest))
            .contentType("application/json"));

        // Assert
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void delete() throws Exception {
        // Arrange
        ShoppingCart shoppingCart = ShoppingCartObjectMother.genericShoppingCart();
        ShoppingCartCK ck = ShoppingCartCK.builder()
                .actor(shoppingCart.getActor().getActorId())
                .product(shoppingCart.getProduct().getProductId())
                .build();
        String requestPath = ApiRoutes.ShoppingCart.Delete;

        // Act
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.delete(requestPath)
            .secure(true)
            .content(asJsonString(ck))
            .contentType("application/json"));

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