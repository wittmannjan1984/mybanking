package org.example.repository;

import org.example.entity.Agreement;
import org.example.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgreementRepository extends JpaRepository<Agreement, Long> {

    Optional<Agreement> searchAgreementByProduct(Product product);
}