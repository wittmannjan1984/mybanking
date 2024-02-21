package org.example.service;

import org.example.entity.Product;
import org.example.model.enums.CurrencyCode;
import org.example.model.enums.ProductStatus;
import org.example.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductServiceImpl productService;

    private List<Product> products;

    @BeforeEach
    void init() {
        products = List.of(
                new Product(1L, "Test product 1", ProductStatus.ACTIVE, CurrencyCode.CHF, 15.2, 50000),
                new Product(2L, "Test product 2", ProductStatus.ACTIVE, CurrencyCode.EUR, 15.2, 30000));
    }

    @Test
    void getAll() {
        Mockito.when(repository.findAll()).thenReturn(products);
        Assertions.assertEquals(2, productService.getAll().size());
    }

    @Test
    void getByIdWhenProductExists() {
        Mockito.when(repository.findById(products.get(0).getId())).thenReturn(Optional.ofNullable(products.get(0)));
        Assertions.assertEquals(1L, productService.getById(products.get(0).getId()).getId());
    }

    @Test
    void createIfNotExists() {
        Mockito.when(repository.save(products.get(0))).thenReturn(products.get(0));
        Product created = productService.create(products.get(0));
        Assertions.assertEquals(products.get(0), created);
    }

    @Test
    void deleteProductById() {
    }
}