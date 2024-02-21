package org.example.service;

import org.example.entity.Agreement;
import org.example.repository.AgreementRepository;
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
class AgreementServiceImplTest {

    @Mock
    private AgreementRepository repository;

    @InjectMocks
    private AgreementServiceImpl agreementService;

    private List<Agreement> agreements;

    @BeforeEach
    void init() {
        agreements = List.of(
                new Agreement(1L, 1.5, 34, 2600),
                new Agreement(2L, 2.5, 34, 2600));
    }

    @Test
    void getAll() {
        Mockito.when(repository.findAll()).thenReturn(agreements);
        Assertions.assertEquals(2, agreementService.getAll().size());
    }

    @Test
    void getByIdWhenAgreementExists() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.ofNullable(agreements.get(0)));
        Assertions.assertEquals(1L, agreementService.getById(agreements.get(0).getId()).getId());
    }

    @Test
    void createIfNotExists() {
        Mockito.when(repository.save(agreements.get(0))).thenReturn(agreements.get(0));
        Agreement created = agreementService.create(agreements.get(0));
        Assertions.assertEquals(agreements.get(0), created);
    }

    @Test
    void deleteAgreementById() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.ofNullable(agreements.get(0)));
        agreementService.deleteAgreementById(agreements.get(0).getId());
        Mockito.verify(repository).delete(agreements.get(0));
    }
}