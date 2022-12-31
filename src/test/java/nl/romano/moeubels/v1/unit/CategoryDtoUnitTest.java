package nl.romano.moeubels.v1.unit;

import nl.romano.moeubels.controller.v1.request.create.CreateCategoryRequest;
import nl.romano.moeubels.controller.v1.request.update.UpdateCategoryRequest;
import nl.romano.moeubels.controller.v1.response.CategoryResponse;
import nl.romano.moeubels.model.Category;
import nl.romano.moeubels.v1.utils.CategoryObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

public class CategoryDtoUnitTest {
    private final ModelMapper modelMapper = new ModelMapper();

    @Test
    public void whenCategoryModelToCategoryResponseDto_thenCorrect() {
        // Arrange
        Category category = CategoryObjectMother.genericCategory();

        // Act
        CategoryResponse categoryResponse = modelMapper.map(category, CategoryResponse.class);

        // Assert
        Assertions.assertEquals(category.getCategoryId(), categoryResponse.getCategoryId());
        Assertions.assertEquals(category.getCategoryName(), categoryResponse.getCategoryName());
        Assertions.assertEquals(category.getCategoryDescription(), categoryResponse.getCategoryDescription());
    }

    @Test
    public void whenUpdateCategoryRequestToCategoryModel_thenCorrect() {
        // Arrange
        UpdateCategoryRequest updateCategoryRequest = CategoryObjectMother.genericUpdateCategoryRequest();

        // Act
        Category category = modelMapper.map(updateCategoryRequest, Category.class);

        // Assert
        Assertions.assertEquals(updateCategoryRequest.getCategoryName(), category.getCategoryName());
        Assertions.assertEquals(updateCategoryRequest.getCategoryDescription(), category.getCategoryDescription());
    }

    @Test
    public void whenCreateCategoryRequestToCategoryModel_thenCorrect() {
        // Arrange
        CreateCategoryRequest createCategoryRequest = CategoryObjectMother.genericCreateCategoryRequest();

        // Act
        Category category = modelMapper.map(createCategoryRequest, Category.class);

        // Assert
        Assertions.assertEquals(createCategoryRequest.getCategoryName(), category.getCategoryName());
        Assertions.assertEquals(createCategoryRequest.getCategoryDescription(), category.getCategoryDescription());
    }
}
