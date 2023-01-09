package nl.romano.moeubels.v1.unit;

import nl.romano.moeubels.controller.v1.request.create.CreateProductRequest;
import nl.romano.moeubels.controller.v1.request.update.UpdateProductRequest;
import nl.romano.moeubels.controller.v1.response.ProductResponse;
import nl.romano.moeubels.model.Product;
import nl.romano.moeubels.v1.utils.ProductObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

public class ProductDtoUnitTest {
    private final ModelMapper modelMapper = new ModelMapper();

    @Test
    public void whenProductModelToProductResponseDto_thenCorrect() {
        // Arrange
        Product product = ProductObjectMother.genericProduct();

        // Act
        ProductResponse productResponse = modelMapper.map(product, ProductResponse.class);

        // Assert
        Assertions.assertEquals(product.getProductId(), productResponse.getProductId());
        Assertions.assertEquals(product.getProductName(), productResponse.getProductName());
        Assertions.assertEquals(product.getAmountAvailable(), productResponse.getAmountAvailable());
        Assertions.assertEquals(product.getProductDescription(), productResponse.getProductDescription());
        Assertions.assertEquals(product.getCategory().getCategoryId(), productResponse.getCategory().getCategoryId());
        Assertions.assertEquals(product.getImagePath(), productResponse.getImagePath());
        Assertions.assertEquals(product.getPrice(), productResponse.getPrice());
    }

    @Test
    public void whenUpdateProductRequestToProductModel_thenCorrect() {
        // Arrange
        UpdateProductRequest updateProductRequest = ProductObjectMother.genericUpdateProductRequest();

        // Act
        Product product = modelMapper.map(updateProductRequest, Product.class);

        // Assert
        Assertions.assertEquals(updateProductRequest.getProductName(), product.getProductName());
        Assertions.assertEquals(updateProductRequest.getAmountAvailable(), product.getAmountAvailable());
        Assertions.assertEquals(updateProductRequest.getProductDescription(), product.getProductDescription());
        Assertions.assertEquals(updateProductRequest.getCategoryId(), product.getCategory().getCategoryId());
        Assertions.assertEquals(updateProductRequest.getImagePath(), product.getImagePath());
//        Assertions.assertEquals(updateProductRequest.getIsOnSale(), product.getIsOnSale());
        Assertions.assertEquals(updateProductRequest.getPrice(), product.getPrice());
    }

    @Test
    public void whenCreateProductRequestToProductModel_thenCorrect() {
        // Arrange
        CreateProductRequest createProductRequest = ProductObjectMother.genericCreateProductRequest();

        // Act
        Product product = modelMapper.map(createProductRequest, Product.class);

        // Assert
        Assertions.assertEquals(createProductRequest.getProductName(), product.getProductName());
        Assertions.assertEquals(createProductRequest.getAmountAvailable(), product.getAmountAvailable());
        Assertions.assertEquals(createProductRequest.getProductDescription(), product.getProductDescription());
        Assertions.assertEquals(createProductRequest.getCategoryName(), product.getCategory().getCategoryName());
        Assertions.assertEquals(createProductRequest.getImagePath(), product.getImagePath());
        Assertions.assertEquals(createProductRequest.getPrice(), product.getPrice());
    }
}
