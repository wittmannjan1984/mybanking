package org.example.service.dtoconverter;

public interface Converter<D, E> {

    D toDto(E entity);

    E toEntity(D dto);
}