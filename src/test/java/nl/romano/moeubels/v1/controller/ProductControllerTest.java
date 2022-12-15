package nl.romano.moeubels.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.romano.moeubels.controller.v1.ProductController;
import nl.romano.moeubels.dao.ProductDao;
import nl.romano.moeubels.model.Product;
import nl.romano.moeubels.v1.utils.ProductObjectMother;
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
@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private ProductDao productDao;
    private MockMvc mvc;
    private Product product;

    @BeforeEach
    void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.product = ProductObjectMother.genericProduct();
    }

    @Test
    void getAll() {
    }

    @Test
    void getById() throws Exception {
        Product testProduct = this.product;
        UUID productId = testProduct.getProductId();
        //String actorJsonString = new JSONObject(testActor).toString();
        String requestPath = String.format("/products/%s", productId.toString());

        given(productDao.getById(productId)).willReturn(Optional.of(testProduct));

        this.mvc.perform(MockMvcRequestBuilders
                .get(requestPath).secure(true))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(testProduct)));
    }

    @Test
    void create() throws Exception {
        Product testProduct = this.product;

        this.mvc.perform(MockMvcRequestBuilders.put("/products")
                .secure(true).content(asJsonString(testProduct)).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void update() throws Exception {
        Product testProduct = this.product;

        this.mvc.perform(MockMvcRequestBuilders.post("/products")
                .secure(true).content(asJsonString(testProduct)).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void delete() throws Exception {
        Product testProduct = this.product;
        UUID productId = testProduct.getProductId();
        String requestPath = String.format("/products/%s", productId.toString());

        this.mvc.perform(MockMvcRequestBuilders.delete(requestPath, testProduct).secure(true))
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
