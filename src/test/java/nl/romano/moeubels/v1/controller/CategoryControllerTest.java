package nl.romano.moeubels.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.romano.moeubels.controller.v1.CategoryController;
import nl.romano.moeubels.dao.CategoryDao;
import nl.romano.moeubels.model.Category;
import nl.romano.moeubels.v1.utils.CategoryObjectMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private Page<Category> categoryPage;

    @BeforeEach
    void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.category = CategoryObjectMother.genericCategory();
        this.categoryPage = CategoryObjectMother.genericCategoryPage();
    }

    @Test
    void getAll() throws Exception {
        Page<Category> testCategoryPage = this.categoryPage;
        //String actorJsonString = new JSONObject(testActor).toString();
        String requestPath = "/categories/all";
        Pageable pageable = Pageable.ofSize(5).withPage(0);
        String requestBody = String.format("{\"size\": %o, \"page: %o\"}", pageable.getPageSize(), pageable.getOffset());

        given(categoryDao.getAll(pageable)).willReturn(CategoryObjectMother.genericCategoryPage());

        this.mvc.perform(MockMvcRequestBuilders
                        .get(requestPath).secure(true)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(testCategoryPage)));
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
