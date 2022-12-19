package nl.romano.moeubels.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.romano.moeubels.contract.v1.ApiRoutes;
import nl.romano.moeubels.controller.v1.CategoryController;
import nl.romano.moeubels.controller.v1.request.create.CreateCategoryRequest;
import nl.romano.moeubels.controller.v1.request.update.UpdateCategoryRequest;
import nl.romano.moeubels.controller.v1.response.CategoryResponse;
import nl.romano.moeubels.dao.CategoryDao;
import nl.romano.moeubels.model.Category;
import nl.romano.moeubels.v1.utils.CategoryObjectMother;
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
@WebMvcTest(CategoryController.class)
class CategoryControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private CategoryDao categoryDao;
    @Autowired
    private MockMvc mvc;

    private final ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getById() throws Exception {
        // Arrange
        Category category = CategoryObjectMother.genericCategory();
        CategoryResponse categoryResponse = modelMapper.map(category, CategoryResponse.class);
        UUID categoryId = category.getCategoryId();
        String requestPath = ApiRoutes.Category.Get.replace("{id}", categoryId.toString());

        given(categoryDao.getById(categoryId)).willReturn(Optional.of(category));

        // Act
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.get(requestPath).secure(true));

        // Assert
        result.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(asJsonString(categoryResponse)));
    }

    @Test
    void getAll() throws Exception {
        // Arrange
        Page<Category> categoryPage = CategoryObjectMother.genericCategoryPage();
        Pageable pageable = Pageable.ofSize(5).withPage(0);
        Page<CategoryResponse> categoryResponsePage =
            CategoryObjectMother.genericCategoryResponsePageFromCategoryPage(categoryPage, pageable);
        String requestPath = ApiRoutes.Category.GetAll;
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("page", String.valueOf(pageable.getPageNumber()));
        requestParams.add("size", String.valueOf(pageable.getPageSize()));

        given(categoryDao.getAll(pageable)).willReturn(categoryPage);

        // Act
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.get(requestPath).secure(true).params(requestParams));

        // Assert
        result.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(asJsonString(categoryResponsePage)));
    }

    @Test
    void create() throws Exception {
        // Arrange
        CreateCategoryRequest categoryRequest = CategoryObjectMother.genericCreateCategoryRequest();
        String requestPath = ApiRoutes.Category.Create;

        // Act
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.post(requestPath)
            .secure(true)
            .content(asJsonString(categoryRequest))
            .contentType("application/json"));

        // Assert
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void update() throws Exception {
        // Arrange
        UUID categoryId = UUID.randomUUID();
        UpdateCategoryRequest categoryRequest = CategoryObjectMother.genericUpdateCategoryRequest();
        String requestPath = ApiRoutes.Category.Update.replace("{id}", categoryId.toString());

        // Act
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.put(requestPath)
            .secure(true).content(asJsonString(categoryRequest)).contentType("application/json"));

        // Assert
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void delete() throws Exception {
        // Arrange
        Category category = CategoryObjectMother.genericCategory();
        UUID categoryId = category.getCategoryId();
        String requestPath = ApiRoutes.Category.Delete.replace("{id}", categoryId.toString());

        // Act
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.delete(requestPath, category).secure(true));

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
