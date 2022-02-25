package nl.romano.moeubels.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.romano.moeubels.dao.CategoryDao;
import nl.romano.moeubels.model.Actor;
import nl.romano.moeubels.model.Category;
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
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CategoryController.class)
class CategoryControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private CategoryDao categoryDao;
    private MockMvc mvc;
    private Category category;

    @BeforeEach
    void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.category = Category.builder()
                .categoryId(UUID.randomUUID())
                .categoryName("Name")
                .categoryDescription("Description")
                .createdAt(ZonedDateTime.now())
                .modifiedAt(ZonedDateTime.now())
                .build();
    }

    @Test
    void getAll() {
        //Category testCategory = this.category;

    }

    @Test
    void getById() throws Exception {
        Category testCategory = this.category;
        UUID categoryId = testCategory.getCategoryId();
        //String actorJsonString = new JSONObject(testActor).toString();
        String requestPath = String.format("/categories/%s", categoryId.toString());

        given(categoryDao.getById(categoryId)).willReturn(Optional.of(testCategory));

        this.mvc.perform(MockMvcRequestBuilders
                .get(requestPath).secure(true))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(testCategory)));
    }

    @Test
    void create() throws Exception {
        Category testCategory = this.category;

        this.mvc.perform(MockMvcRequestBuilders.put("/categories")
                .secure(true).content(asJsonString(testCategory)).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void update() throws Exception {
        Category testCategory = this.category;

        this.mvc.perform(MockMvcRequestBuilders.post("/categories")
                .secure(true).content(asJsonString(testCategory)).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void delete() throws Exception {
        Category testCategory = this.category;
        UUID categoryId = testCategory.getCategoryId();
        String requestPath = String.format("/categories/%s", categoryId.toString());

        this.mvc.perform(MockMvcRequestBuilders.delete(requestPath, testCategory).secure(true))
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
