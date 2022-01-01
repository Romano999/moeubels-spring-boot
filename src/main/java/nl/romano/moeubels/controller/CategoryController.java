package nl.romano.moeubels.controller;

import nl.romano.moeubels.dao.CategoryDao;
import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@RequestMapping("/categories")
public class CategoryController implements CrudOperations<Category> {
    @Autowired
    private CategoryDao categoryDao;

    @GetMapping("/all")
    public ResponseEntity<?> getAll(int page) {
        Page<Category> categories = categoryDao.getAll(Pageable.ofSize(5).withPage(page));
        return Responses.ResponseEntityOk(categories);
    }

    @Override
    public ResponseEntity<?> getById(UUID uuid) throws Exception {
        Category category = categoryDao.getById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id: " + uuid + "not found"));
        return Responses.ResponseEntityOk(category);
    }

    @Override
    public ResponseEntity<String> create(@RequestBody Category category) {
        categoryDao.save(category);
        return Responses.jsonOkResponseEntity();
    }

    @Override
    public ResponseEntity<String> update(@RequestBody Category category) {
        categoryDao.save(category);
        return Responses.jsonOkResponseEntity();
    }

    @Override
    public ResponseEntity<?> delete(UUID uuid) throws Exception {
        categoryDao.delete(uuid);
        return Responses.jsonOkResponseEntity();
    }
}
