package nl.romano.moeubels.controller.v1;

import nl.romano.moeubels.contract.v1.ApiRoutes;
import nl.romano.moeubels.contract.v1.request.create.CreateProductRequest;
import nl.romano.moeubels.contract.v1.request.update.UpdateProductRequest;
import nl.romano.moeubels.contract.v1.response.ProductResponse;
import nl.romano.moeubels.dao.ProductDao;
import nl.romano.moeubels.exceptions.ProductNotFoundException;
import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.model.Product;
import nl.romano.moeubels.utils.Responses;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class ProductController {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping(value = ApiRoutes.Product.GetAll, params = { "page", "size" })
    public ResponseEntity<Page<Product>> getAll(@RequestParam int page, @RequestParam int size) {
        logger.info("Getting all products on page " + page + " with size " + size);
        Page<Product> products = productDao.getAll(Pageable.ofSize(size).withPage(page));
        return Responses.ResponseEntityOk(products);
    }

    @GetMapping(value = "/{searchTerm}", params = { "page", "size" })
    public ResponseEntity<Page<Product>> getByName(@PathVariable String searchTerm, @RequestParam int page, @RequestParam int size) {
        logger.info("Getting all products with search term " + searchTerm + " on page " + page + " with size " + size);
        Page<Product> products = productDao.getByName(searchTerm, Pageable.ofSize(size).withPage(page));
        return Responses.ResponseEntityOk(products);
    }

    @GetMapping(ApiRoutes.Product.Get)
    public ResponseEntity<ProductResponse> getById(@PathVariable UUID id) throws ResourceNotFoundException {
        logger.info("Getting a product with product id " + id);
        Product product = productDao.getById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exc = new ProductNotFoundException("Product with product id " + id + " not found");
                    logger.error(exc.getMessage());
                    return exc;
                });

        ProductResponse productResponse = convertEntityToDto(product);
        return Responses.ResponseEntityOk(productResponse);
    }

    @PostMapping(ApiRoutes.Product.Create)
    public ResponseEntity<String> create(@RequestBody CreateProductRequest productRequest) {
        Product product = convertDtoToEntity(productRequest);
        logger.info("Creating a product");
        productDao.save(product);
        return Responses.jsonOkResponseEntity();
    }

    @PutMapping(ApiRoutes.Product.Update)
    public ResponseEntity<String> update(@RequestBody UpdateProductRequest productRequest) {
        Product product = convertDtoToEntity(productRequest);
        logger.info("Updating a product");
        productDao.update(product);
        return Responses.jsonOkResponseEntity();
    }

    @DeleteMapping(ApiRoutes.Product.Delete)
    public ResponseEntity<?> delete(@PathVariable UUID id) throws ResourceNotFoundException{
        logger.info("Deleting a product with product id " + id);
        productDao.delete(id);
        return Responses.jsonOkResponseEntity();
    }

    private Product convertDtoToEntity(UpdateProductRequest updateProductRequest) {
        logger.info("Mapping update product request to product model");
        return modelMapper.map(updateProductRequest, Product.class);
    }

    private Product convertDtoToEntity(CreateProductRequest createProductRequest) {
        logger.info("Mapping create product request to product model");
        return modelMapper.map(createProductRequest, Product.class);
    }

    private ProductResponse convertEntityToDto(Product product) {
        logger.info("Mapping product model to product response");
        return modelMapper.map(product, ProductResponse.class);
    }
}
