package org.example.service.dtoconverter;

import org.example.entity.Transaction;
import org.example.model.dto.TransactionDto;
import org.springframework.stereotype.Service;

@Service
public class TransactionDtoConverter implements Converter<TransactionDto, Transaction> {

    public TransactionDto toDto(Transaction transaction) {
        return new TransactionDto(
                transaction.getId(),
                transaction.getDebitAccount().getIban(),
                transaction.getCreditAccount().getIban(),
                transaction.getTransactionType(),
                transaction.getAmount(),
                transaction.getDescription());
    }

    public Transaction toEntity(TransactionDto transactionDto) {
        throw new UnsupportedOperationException();
    }
}