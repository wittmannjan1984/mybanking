package org.example.service;

import org.example.entity.Product;
import org.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Product with id %d not found", id)));
    }

    @Override
    public Product create(Product product) {
        Optional<Product> productEntity = productRepository.findProductByNameAndCurrencyCode(product.getName(), product.getCurrencyCode());
        if (productEntity.isPresent()) {
            throw new EntityExistsException("This product already exists");
        }
        return productRepository.save(product);
    }

    @Override
    public void deleteProductById(long id) {
        productRepository.delete(getById(id));
    }
}