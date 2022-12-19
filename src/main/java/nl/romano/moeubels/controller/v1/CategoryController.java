package nl.romano.moeubels.controller.v1;

import nl.romano.moeubels.contract.v1.ApiRoutes;
import nl.romano.moeubels.controller.v1.request.create.CreateCategoryRequest;
import nl.romano.moeubels.controller.v1.request.update.UpdateCategoryRequest;
import nl.romano.moeubels.controller.v1.response.CategoryResponse;
import nl.romano.moeubels.dao.CategoryDao;
import nl.romano.moeubels.exceptions.CategoryNotFoundException;
import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.model.Category;
import nl.romano.moeubels.utils.JsonConverter;
import nl.romano.moeubels.utils.Responses;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
public class CategoryController {
    @Autowired
    private CategoryDao categoryDao;

    private final ModelMapper modelMapper = new ModelMapper();
    private final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @GetMapping(ApiRoutes.Category.Get)
    public ResponseEntity<CategoryResponse> getById(@PathVariable UUID id) throws ResourceNotFoundException {
        logger.info("Received following category id '" + id + "'");
        logger.info("Getting a category by category id '" + id + "'");
        Category category = categoryDao.getById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exc = new CategoryNotFoundException("Category with category id '" + id + "' not found");
                    logger.error(exc.getMessage());
                    return exc;
                });

        logger.info("Category with id '" + category.getCategoryId() + "' found");
        CategoryResponse categoryResponse = convertEntityToDto(category);
        logger.info("Returning following data: " + JsonConverter.asJsonString(categoryResponse));
        return Responses.ResponseEntityOk(categoryResponse);
    }

    @GetMapping(value = ApiRoutes.Category.GetAll, params = { "page", "size" })
    public ResponseEntity<Page<CategoryResponse>> getAll(@RequestParam int page, @RequestParam int size) {
        logger.info("Getting all categories on page " + page + " with size " + size);
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<Category> categories = categoryDao.getAll(pageable);

        Page<CategoryResponse> categoryResponses = convertEntityPageToDtoPage(categories, pageable);
        return Responses.ResponseEntityOk(categoryResponses);
    }

    @PostMapping(ApiRoutes.Category.Create)
    public ResponseEntity<String> create(@RequestBody CreateCategoryRequest categoryRequest) {
        logger.info("Received following create category request '" + JsonConverter.asJsonString(categoryRequest) + "'");
        Category category = convertDtoToEntity(categoryRequest);
        logger.info("Creating a category");
        categoryDao.save(category);
        return Responses.jsonOkResponseEntity();
    }

    @PutMapping(ApiRoutes.Category.Update)
    public ResponseEntity<String> update(@RequestBody UpdateCategoryRequest categoryRequest) {
        logger.info("Received following update category request '" + JsonConverter.asJsonString(categoryRequest) + "'");
        Category category = convertDtoToEntity(categoryRequest);
        logger.info("Updating a category");
        categoryDao.update(category);
        return Responses.jsonOkResponseEntity();
    }

    @DeleteMapping(ApiRoutes.Category.Delete)
    public ResponseEntity<?> delete(@PathVariable UUID id) throws ResourceNotFoundException {
        logger.info("Deleting a category with category id '" + id + "'");
        categoryDao.delete(id);
        return Responses.jsonOkResponseEntity();
    }

    private Category convertDtoToEntity(UpdateCategoryRequest updateCategoryRequest) {
        logger.info("Mapping update category request to category model");
        return modelMapper.map(updateCategoryRequest, Category.class);
    }

    private Category convertDtoToEntity(CreateCategoryRequest createCategoryRequest) {
        logger.info("Mapping create category request to category model");
        return modelMapper.map(createCategoryRequest, Category.class);
    }

    private CategoryResponse convertEntityToDto(Category category) {
        logger.info("Mapping category model to category response");
        return modelMapper.map(category, CategoryResponse.class);
    }

    private Page<CategoryResponse> convertEntityPageToDtoPage(Page<Category> categories, Pageable pageable) {
        logger.info("Mapping a category page to a category response page");
        ArrayList<CategoryResponse> categoryResponses = new ArrayList<>();
        categories.forEach(category -> categoryResponses.add(convertEntityToDto(category)));
        logger.info("Done with mapping a category page to a category response page");
        return new PageImpl<CategoryResponse>(categoryResponses, pageable, categoryResponses.size());
    }
}
