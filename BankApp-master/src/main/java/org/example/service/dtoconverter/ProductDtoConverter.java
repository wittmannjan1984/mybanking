package org.example.service.dtoconverter;

import org.example.entity.Product;
import org.example.model.dto.ProductDto;
import org.springframework.stereotype.Service;

@Service
public class ProductDtoConverter implements Converter<ProductDto, Product> {

    public ProductDto toDto(Product product) {
        if (product == null) {
            return null;
        }
        return new ProductDto(product.getId(), product.getManager().getFirstName(), product.getManager().getLastName(),
                product.getName(),
                product.getStatus(), product.getCurrencyCode(), product.getInterestRate(),
                product.getProductLimit());
    }

    public Product toEntity(ProductDto productDto) {
        return null;
    }
}