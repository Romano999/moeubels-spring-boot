package nl.romano.moeubels.controller;

import nl.romano.moeubels.dao.CategoryDao;
import nl.romano.moeubels.exceptions.CategoryNotFoundException;
import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class CategoryController implements CrudOperations<Category> {
    @Autowired
    private CategoryDao categoryDao;

    @GetMapping("/all/{page}")
    public ResponseEntity<?> getAll(@PathVariable int page) {
        Page<Category> categories = categoryDao.getAll(Pageable.ofSize(5).withPage(page));
        return Responses.ResponseEntityOk(categories);
    }

    @Override
    @GetMapping("/{uuid}")
    public ResponseEntity<?> getById(@PathVariable UUID uuid) throws ResourceNotFoundException {
        Category category = categoryDao.getById(uuid)
                .orElseThrow(() -> new CategoryNotFoundException("Category with id: " + uuid + "not found"));
        return Responses.ResponseEntityOk(category);
    }

    @Override
    @PostMapping()
    public ResponseEntity<String> create(@RequestBody Category category) {
        categoryDao.save(category);
        return Responses.jsonOkResponseEntity();
    }

    @Override
    @PutMapping()
    public ResponseEntity<String> update(@RequestBody Category category) {
        categoryDao.update(category);
        return Responses.jsonOkResponseEntity();
    }

    @Override
    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> delete(@PathVariable UUID uuid) throws ResourceNotFoundException {
        categoryDao.delete(uuid);
        return Responses.jsonOkResponseEntity();
    }
}
