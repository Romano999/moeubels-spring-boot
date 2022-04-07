package nl.romano.moeubels.controller;

import nl.romano.moeubels.dao.ProductDao;
import nl.romano.moeubels.exceptions.ActorNotFoundException;
import nl.romano.moeubels.exceptions.ProductNotFoundException;
import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.model.Product;
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
@RequestMapping("/products")
public class ProductController implements CrudOperations<Product> {
    @Autowired
    private ProductDao productDao;

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping(value = "/all", params = { "page", "size" })
    public ResponseEntity<Page<Product>> getAll(@RequestParam int page, @RequestParam int size) {
        logger.info("Getting all Products on page " + page + " with size " + size);
        Page<Product> products = productDao.getAll(Pageable.ofSize(size).withPage(page));
        return Responses.ResponseEntityOk(products);
    }

    @GetMapping(value = "/{searchTerm}", params = { "page", "size" })
    public ResponseEntity<Page<Product>> getByName(@PathVariable String searchTerm, @RequestParam int page, @RequestParam int size) {
        logger.info("Getting all Products with search term " + searchTerm + " on page " + page + " with size " + size);
        Page<Product> products = productDao.getByName(searchTerm, Pageable.ofSize(size).withPage(page));
        return Responses.ResponseEntityOk(products);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable UUID id) throws ResourceNotFoundException {
        logger.info("Getting a Product with id " + id);
        Product product = productDao.getById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exc = new ProductNotFoundException("Product with id: " + id + " not found");
                    logger.error(exc.getMessage());
                    return exc;
                });
        return Responses.ResponseEntityOk(product);
    }

    @Override
    @PostMapping()
    public ResponseEntity<String> create(@RequestBody Product product) {
        logger.info("Creating a Product");
        productDao.save(product);
        return Responses.jsonOkResponseEntity();
    }

    @Override
    @PutMapping()
    public ResponseEntity<String> update(@RequestBody Product product) {
        logger.info("Updating a Product");
        productDao.update(product);
        return Responses.jsonOkResponseEntity();
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) throws ResourceNotFoundException{
        logger.info("Deleting a Product with id " + id);
        productDao.delete(id);
        return Responses.jsonOkResponseEntity();
    }
}
