package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.model.dto.AccountBalanceInfoDto;
import org.example.model.dto.AccountDto;
import org.example.model.dto.TransactionDto;
import org.example.service.AccountService;
import org.example.service.dtoconverter.AccountDtoConverter;
import org.example.service.dtoconverter.TransactionDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Tag(name = "Account controller", description = "Account management")
@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountDtoConverter converter;

    @Autowired
    private TransactionDtoConverter transactionConverter;

    @Operation(summary = "List of all accounts", description = "Obtaining all accounts in the system")
    @SecurityRequirement(name = "basicauth")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    public List<AccountDto> getAll() {
        return accountService.getAll().stream()
                .map(account -> converter.toDto(account)).collect(Collectors.toList());
    }

    @Operation(summary = "List of all accounts for current client", description = "Show all accounts for current logged client")
    @SecurityRequirement(name = "basicauth")
    @GetMapping("/current")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('CLIENT')")
    List<AccountDto> getAllByCurrentClient() {
        return accountService.getAllByCurrentClient().stream()
                .map(account -> converter.toDto(account)).collect(Collectors.toList());
    }

    @Operation(summary = "Create account", description = "Allow to create a new one in the system")
    @SecurityRequirement(name = "basicauth")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('CLIENT')")
    public AccountDto create(@RequestBody AccountDto accountDto) {
        return converter.toDto(accountService.create(converter.toEntity(accountDto)));
    }

    @Operation(summary = "Find account by IBAN", description = "Allow to find a account by IBAN ")
    @SecurityRequirement(name = "basicauth")
    @GetMapping("/{iban}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    public AccountDto getByIban(@PathVariable("iban") String iban) {
        return converter.toDto(accountService.getByIban(iban));
    }

    @Operation(summary = "Retrieving account balance", description = "Show a balance of selected account")
    @SecurityRequirement(name = "basicauth")
    @GetMapping("/balance/{iban}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('CLIENT')")
    public AccountBalanceInfoDto retrievingAccountBalance(@PathVariable("iban") String iban) {
        return accountService.retrievingAccountBalance(iban);
    }

    @Operation(summary = "Transaction history", description = "Show transaction history for selected account sorted by date")
    @SecurityRequirement(name = "basicauth")
    @GetMapping("/history/{iban}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('CLIENT')")
    public List<TransactionDto> getTransactionsHistoryOfAccount(@PathVariable("iban") String iban) {
        return accountService.transactionHistory(iban).stream()
                .map(transactionConverter::toDto).collect(Collectors.toList());
    }

    @Operation(summary = "Delete account by IBAN", description = "Allow to delete an existing account in the system by IBAN")
    @SecurityRequirement(name = "basicauth")
    @DeleteMapping("/{iban}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    public void deleteByIban(@PathVariable("iban") String iban) {
        accountService.deleteAccountByIban(iban);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, String> exceptionHandler(ConstraintViolationException ex) {
        Map<String, String> map = new HashMap<>();
        ex.getConstraintViolations().forEach(error ->
                map.put(error.getPropertyPath().toString(), error.getMessage()));
        return map;
    }
}