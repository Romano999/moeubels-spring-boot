package nl.romano.moeubels.dao;

import nl.romano.moeubels.exceptions.ProductNotFoundException;
import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.model.Product;
import nl.romano.moeubels.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ProductDao implements Dao<Product> {
    @Autowired
    private ProductRepository productRepository;

    public Page<Product> getAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Page<Product> getByName(String searchTerm, Pageable pageable) {
        return productRepository.findAllByProductNameIgnoreCaseContaining(searchTerm, pageable);
    }

    @Override
    public Optional<Product> getById(UUID uuid) {
        return productRepository.findById(uuid);
    }

    @Override
    public void save(Product product) {
        product.setModifiedAt(ZonedDateTime.now());
        product.setCreatedAt(ZonedDateTime.now());
        productRepository.save(product);
    }

    @Override
    public void update(Product product) {
        Product initialProduct = this.getById(product.getProductId()).orElseThrow();
        product.setModifiedAt(ZonedDateTime.now());
        product.setCreatedAt(initialProduct.getCreatedAt());
        productRepository.save(product);
    }

    @Override
    public void delete(UUID uuid) throws ResourceNotFoundException {
        Product product = productRepository.findById(uuid)
                .orElseThrow(() -> new ProductNotFoundException("Product with id: " + uuid + "not found"));
        productRepository.delete(product);
    }
}
