package org.example.repository;

import org.example.entity.Product;
import org.example.model.enums.CurrencyCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findProductByNameAndCurrencyCode(String name, CurrencyCode currencyCode);
}