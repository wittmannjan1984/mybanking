package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.model.dto.TransactionCreateDto;
import org.example.model.dto.TransactionDto;
import org.example.service.TransactionService;
import org.example.service.dtoconverter.TransactionDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Transactions controller", description = "Transactions management")
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionDtoConverter converter;

    @Operation(summary = "Search transaction by amount",
            description = "Allow to find a transaction by amount in the range of 50 monetary units")
    @SecurityRequirement(name = "basicauth")
    @GetMapping("/search/{iban}/{amount}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CLIENT')")
    public List<TransactionDto> searchByAmount(@PathVariable("iban") String iban, @PathVariable("amount") BigDecimal amount) {
        return transactionService.search(iban, amount)
                .stream().map(transaction -> converter.toDto(transaction)).collect(Collectors.toList());
    }

    @Operation(summary = "Money transfer", description = "Allow to transfer money between accounts in the system")
    @SecurityRequirement(name = "basicauth")
    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('CLIENT')")
    public TransactionDto transferMoney(@RequestBody TransactionCreateDto transactionCreate) {
        return converter.toDto(transactionService.transferMoneyBetweenAccounts(transactionCreate.getIbanFrom(),
                transactionCreate.getIbanTo(), transactionCreate.getAmount(), transactionCreate.getDescription()));
    }
}