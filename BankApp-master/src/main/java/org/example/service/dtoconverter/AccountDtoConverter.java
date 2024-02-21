package org.example.service.dtoconverter;

import org.example.entity.Account;
import org.example.model.dto.AccountDto;
import org.springframework.stereotype.Service;

@Service
public class AccountDtoConverter implements Converter<AccountDto, Account> {

    public AccountDto toDto(Account account) {
        return new AccountDto(account.getIban(), account.getClient().getFirstName(), account.getClient().getLastName(),
                account.getName(), account.getType(), account.getStatus(),
                account.getBalance(), account.getCurrencyCode(),
                account.getCreatedAt(), account.getUpdatedAt());
    }

    public Account toEntity(AccountDto accountDto) {
        return new Account(accountDto.getName(), accountDto.getType(), accountDto.getCurrencyCode());
    }
}