package nl.romano.moeubels.controller;

import nl.romano.moeubels.dao.ProductDao;
import nl.romano.moeubels.exceptions.ProductNotFoundException;
import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.model.Product;
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

    @GetMapping("/all/{page}")
    public ResponseEntity<?> getAll(@PathVariable int page) {
        Page<Product> products = productDao.getAll(Pageable.ofSize(5).withPage(page));
        return Responses.ResponseEntityOk(products);
    }

    @Override
    @GetMapping("/{uuid}")
    public ResponseEntity<?> getById(@PathVariable UUID uuid) throws ResourceNotFoundException {
        Product product = productDao.getById(uuid)
                .orElseThrow(() -> new ProductNotFoundException("Product with id: " + uuid + "not found"));
        return Responses.ResponseEntityOk(product);
    }

    @Override
    @PostMapping()
    public ResponseEntity<String> create(@RequestBody Product product) {
        productDao.save(product);
        return Responses.jsonOkResponseEntity();
    }

    @Override
    @PutMapping()
    public ResponseEntity<String> update(@RequestBody Product product) {
        productDao.update(product);
        return Responses.jsonOkResponseEntity();
    }

    @Override
    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> delete(@PathVariable UUID uuid) throws ResourceNotFoundException{
        productDao.delete(uuid);
        return Responses.jsonOkResponseEntity();
    }
}
