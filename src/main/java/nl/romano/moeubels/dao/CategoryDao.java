package nl.romano.moeubels.dao;

import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.model.Category;
import nl.romano.moeubels.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

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

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void update(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void delete(UUID uuid) throws ResourceNotFoundException {
        Category category = categoryRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id: " + uuid + "not found"));
        categoryRepository.delete(category);
    }
}
