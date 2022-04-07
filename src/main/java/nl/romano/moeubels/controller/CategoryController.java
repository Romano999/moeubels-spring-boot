package nl.romano.moeubels.controller;

import nl.romano.moeubels.dao.CategoryDao;
import nl.romano.moeubels.exceptions.CategoryNotFoundException;
import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.model.Category;
import nl.romano.moeubels.utils.Responses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @GetMapping("/all")
    public ResponseEntity<?> getAll(@RequestBody int page, @RequestBody int size) {
        logger.info("Getting all Categories on page " + page + " with size " + size);
        Page<Category> categories = categoryDao.getAll(Pageable.ofSize(size).withPage(page));
        return Responses.ResponseEntityOk(categories);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) throws ResourceNotFoundException {
        logger.info("Getting a Category by id: " + id);
        Category category = categoryDao.getById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exc = new CategoryNotFoundException("Category with id: " + id + " not found");
                    logger.error(exc.getMessage());
                    return exc;
                });
        return Responses.ResponseEntityOk(category);
    }

    @Override
    @PostMapping()
    public ResponseEntity<String> create(@RequestBody Category category) {
        logger.info("Creating a Category");
        categoryDao.save(category);
        return Responses.jsonOkResponseEntity();
    }

    @Override
    @PutMapping()
    public ResponseEntity<String> update(@RequestBody Category category) {
        logger.info("Updating a Category");
        categoryDao.update(category);
        return Responses.jsonOkResponseEntity();
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) throws ResourceNotFoundException {
        logger.info("Deleting a Category with id " + id);
        categoryDao.delete(id);
        return Responses.jsonOkResponseEntity();
    }
}
