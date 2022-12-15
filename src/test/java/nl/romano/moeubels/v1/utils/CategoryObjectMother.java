package nl.romano.moeubels.v1.utils;

import nl.romano.moeubels.controller.v1.request.create.CreateCategoryRequest;
import nl.romano.moeubels.controller.v1.request.update.UpdateCategoryRequest;
import nl.romano.moeubels.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CategoryObjectMother {
    public static Category genericCategory() {
        return Category.builder()
            .categoryId(UUID.randomUUID())
            .categoryName("Name")
            .categoryDescription("Description")
            .createdAt(ZonedDateTime.now())
            .modifiedAt(ZonedDateTime.now())
            .build();
    }

    public static Page<Category> genericCategoryPage() {
        List<Category> categories = new ArrayList<>();
        int pageSize = 5;

        for (int i = 0; i < pageSize; i++) {
            Category newCategory = Category.builder()
                .categoryId(UUID.randomUUID())
                .categoryName("Category " + i)
                .categoryDescription("Description of category " + i)
                .createdAt(ZonedDateTime.now())
                .modifiedAt(ZonedDateTime.now())
                .build();
            categories.add(newCategory);
        }

        return new PageImpl<>(categories);
    }

    public static UpdateCategoryRequest genericUpdateCategoryRequest() {
        return UpdateCategoryRequest.builder()
            .categoryName("Test category")
            .categoryDescription("Test description")
            .build();
    }

    public static CreateCategoryRequest genericCreateCategoryRequest() {
        return CreateCategoryRequest.builder()
            .categoryName("Test category")
            .categoryDescription("Test description")
            .build();
    }
}
