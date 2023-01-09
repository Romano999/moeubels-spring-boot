package nl.romano.moeubels.controller.v1;

import nl.romano.moeubels.contract.v1.ApiRoutes;
import nl.romano.moeubels.controller.v1.request.create.CreateProductRequest;
import nl.romano.moeubels.controller.v1.request.update.UpdateProductRequest;
import nl.romano.moeubels.controller.v1.response.ProductResponse;
import nl.romano.moeubels.dao.CategoryDao;
import nl.romano.moeubels.dao.ProductDao;
import nl.romano.moeubels.exceptions.ProductNotFoundException;
import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.model.Product;
import nl.romano.moeubels.utils.JsonConverter;
import nl.romano.moeubels.utils.Responses;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
public class ProductController {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private CategoryDao categoryDao;

    private final ModelMapper modelMapper = new ModelMapper();
    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping(value = ApiRoutes.Product.GetAll, params = { "page", "size" })
    public ResponseEntity<Page<ProductResponse>> getAll(@RequestParam int page, @RequestParam int size) {
        logger.info("Getting all products on page " + page + " with size " + size);
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<Product> products = productDao.getAll(pageable);
        logger.info("Found pageable with size " + products.getTotalElements() + " and total pages " + products.getTotalPages());
        Page<ProductResponse> productResponses = convertEntityPageToDtoPage(products, pageable);
        logger.info("Sending pageable with size " + productResponses.getTotalElements() + " and total pages " + productResponses.getTotalPages());
        return Responses.ResponseEntityOk(productResponses);
    }

    @GetMapping(value = ApiRoutes.Product.GetBySearchTerm, params = { "page", "size" })
    public ResponseEntity<Page<ProductResponse>> getByName(@PathVariable String searchTerm, @RequestParam int page, @RequestParam int size) {
        logger.info("Getting all products with search term " + searchTerm + " on page " + page + " with size " + size);
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<Product> products = productDao.getByName(searchTerm, pageable);

        Page<ProductResponse> productResponses = convertEntityPageToDtoPage(products, pageable);
        return Responses.ResponseEntityOk(productResponses);
    }

    @GetMapping(ApiRoutes.Product.Get)
    public ResponseEntity<ProductResponse> getById(@PathVariable UUID id) throws ResourceNotFoundException {
        logger.info("Received following product id '" + id + "'");
        logger.info("Getting a product with product id '" + id + "'");
        Product product = productDao.getById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exc = new ProductNotFoundException("Product with product id '" + id + "' not found");
                    logger.error(exc.getMessage());
                    return exc;
                });

        logger.info("Product with id '" + product.getProductId() + "' found");
        ProductResponse productResponse = convertEntityToDto(product);
        logger.info("Returning following data: " + JsonConverter.asJsonString(productResponse));
        return Responses.ResponseEntityOk(productResponse);
    }

    @PostMapping(ApiRoutes.Product.Create)
    public ResponseEntity<String> create(@RequestBody CreateProductRequest productRequest) throws ResourceNotFoundException {
        logger.info("Received following create product request '" + JsonConverter.asJsonString(productRequest) + "'");
        Product product = convertDtoToEntity(productRequest);
        logger.info("Searching a category by name " + productRequest.getCategoryName());
        product.setCategory(categoryDao.getByName(productRequest.getCategoryName()));
        logger.info("Creating a product");
        productDao.save(product);
        return Responses.jsonOkResponseEntity();
    }

    @PutMapping(ApiRoutes.Product.Update)
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody UpdateProductRequest productRequest) {
        logger.info("Received following update product request '" + JsonConverter.asJsonString(productRequest) + "'");
        Product product = convertDtoToEntity(productRequest);
        product.setProductId(id);
        logger.info("Updating a product to '" + JsonConverter.asJsonString(product) + "'");
        productDao.update(product);
        return Responses.jsonOkResponseEntity();
    }

    @DeleteMapping(ApiRoutes.Product.Delete)
    public ResponseEntity<?> delete(@PathVariable UUID id) throws ResourceNotFoundException {
        logger.info("Deleting a product with product id '" + id + "'");
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

    private Page<ProductResponse> convertEntityPageToDtoPage(Page<Product> products, Pageable pageable) {
        logger.info("Mapping a product page to a product response page");
        ArrayList<ProductResponse> productResponses = new ArrayList<>();
        products.forEach(product -> productResponses.add(convertEntityToDto(product)));
        logger.info("Done with mapping a product page to a product response page");
        return new PageImpl<ProductResponse>(productResponses, pageable, products.getTotalElements());
    }
}
