package nl.romano.moeubels.dao;

import nl.romano.moeubels.exceptions.CategoryNotFoundException;
import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.model.Category;
import nl.romano.moeubels.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CategoryDao implements Dao<Category> {
    @Autowired
    private CategoryRepository categoryRepository;

    public Page<Category> getAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Optional<Category> getById(UUID uuid) {
        return categoryRepository.findById(uuid);
    }

    public Category getByName(String categoryName) throws ResourceNotFoundException {
        return Optional.of(categoryRepository.findByCategoryNameIgnoreCaseContaining(categoryName))
            .orElseThrow(() -> new CategoryNotFoundException("Category with name: " + categoryName + "not found"));
    }

    @Override
    public void save(Category category) {
        Category initialCategory = getById(category.getCategoryId()).orElseThrow();
        category.setCreatedAt(initialCategory.getCreatedAt());
        category.setModifiedAt(ZonedDateTime.now());
        categoryRepository.save(category);
    }

    @Override
    public void update(Category category) {
        category.setCreatedAt(ZonedDateTime.now());
        category.setModifiedAt(ZonedDateTime.now());
        categoryRepository.save(category);
    }

    @Override
    public void delete(UUID uuid) throws ResourceNotFoundException {
        Category category = categoryRepository.findById(uuid)
                .orElseThrow(() -> new CategoryNotFoundException("Category with id: " + uuid + "not found"));
        categoryRepository.delete(category);
    }
}
