package nl.romano.moeubels.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.romano.moeubels.contract.v1.ApiRoutes;
import nl.romano.moeubels.controller.v1.ProductController;
import nl.romano.moeubels.controller.v1.request.create.CreateProductRequest;
import nl.romano.moeubels.controller.v1.request.update.UpdateProductRequest;
import nl.romano.moeubels.controller.v1.response.ProductResponse;
import nl.romano.moeubels.dao.ProductDao;
import nl.romano.moeubels.model.Product;
import nl.romano.moeubels.v1.utils.ProductObjectMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
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
@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private ProductDao productDao;
    private MockMvc mvc;

    private final ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getAll() throws Exception {
        // Arrange
        Page<Product> productPage = ProductObjectMother.genericProductPage();
        Pageable pageable = Pageable.ofSize(5).withPage(0);
        Page<ProductResponse> productResponsePage =
            ProductObjectMother.genericProductResponsePageFromProductPage(productPage, pageable);
        String requestPath = ApiRoutes.Product.GetAll;
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("page", String.valueOf(pageable.getPageNumber()));
        requestParams.add("size", String.valueOf(pageable.getPageSize()));

        given(productDao.getAll(pageable)).willReturn(productPage);

        // Act
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.get(requestPath).secure(true).params(requestParams));

        // Assert
        result.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(asJsonString(productResponsePage)));
    }

    @Test
    void getByName() throws Exception {
        // Arrange
        String searchTerm = "Table";
        Page<Product> productPage = ProductObjectMother.genericProductPageWithName(searchTerm);
        Pageable pageable = Pageable.ofSize(5).withPage(0);
        Page<ProductResponse> productResponsePage =
            ProductObjectMother.genericProductResponsePageFromProductPage(productPage, pageable);
        String requestPath = ApiRoutes.Product.GetBySearchTerm.replace("{searchTerm}", searchTerm);
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("page", String.valueOf(pageable.getPageNumber()));
        requestParams.add("size", String.valueOf(pageable.getPageSize()));

        given(productDao.getByName(searchTerm, pageable)).willReturn(productPage);

        // Act
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.get(requestPath).secure(true).params(requestParams));

        // Assert
        result.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(asJsonString(productResponsePage)));
    }


    @Test
    void getById() throws Exception {
        // Arrange
        Product product = ProductObjectMother.genericProduct();
        ProductResponse productResponse = modelMapper.map(product, ProductResponse.class);
        UUID productId = product.getProductId();
        String requestPath = ApiRoutes.Product.Get.replace("{id}", productId.toString());

        given(productDao.getById(productId)).willReturn(Optional.of(product));

        // Act
        ResultActions request = this.mvc.perform(MockMvcRequestBuilders.get(requestPath).secure(true));

        // Assert
        request.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(asJsonString(productResponse)));
    }

    @Test
    void create() throws Exception {
        // Arrange
        CreateProductRequest productRequest = ProductObjectMother.genericCreateProductRequest();
        String requestPath = ApiRoutes.Product.Create;

        // Act
        ResultActions result =this.mvc.perform(MockMvcRequestBuilders.post(requestPath)
            .secure(true).content(asJsonString(productRequest)).contentType("application/json"));

        // Assert
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void update() throws Exception {
        // Arrange
        UUID productId = UUID.randomUUID();
        UpdateProductRequest productRequest = ProductObjectMother.genericUpdateProductRequest();
        String requestPath = ApiRoutes.Product.Update.replace("{id}", productId.toString());

        // Act
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.put(requestPath)
                .secure(true).content(asJsonString(productRequest)).contentType("application/json"));

        // Assert
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void delete() throws Exception {
        // Arrange
        Product product = ProductObjectMother.genericProduct();
        UUID productId = product.getProductId();
        String requestPath = ApiRoutes.Product.Delete.replace("{id}", productId.toString());

        // Act
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.delete(requestPath, product).secure(true));

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
