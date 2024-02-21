package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.model.dto.ManagerCreateDto;
import org.example.model.dto.ManagerDto;
import org.example.service.ManagerService;
import org.example.service.dtoconverter.ManagerCreateDtoConverter;
import org.example.service.dtoconverter.ManagerDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Tag(name = "Manager controller", description = "Management of bank manager")
@RestController
@RequestMapping("/managers")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @Autowired
    private ManagerDtoConverter converter;

    @Autowired
    private ManagerCreateDtoConverter createDtoConverter;

    @Operation(summary = "List of all managers", description = "Obtaining all managers in the system")
    @SecurityRequirement(name = "basicauth")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    public List<ManagerDto> getAll() {
        return managerService.getAll().stream()
                .map(manager -> converter.toDto(manager)).collect(Collectors.toList());
    }

    @Operation(summary = "Create manager", description = "Allow to create a new one in the system")
    @SecurityRequirement(name = "basicauth")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    public ManagerDto create(@RequestBody ManagerCreateDto managerDto) {
        return converter.toDto(managerService.create(createDtoConverter.toEntity(managerDto)));
    }

    @Operation(summary = "Find manager by ID", description = "Allow to find a manager by ID ")
    @SecurityRequirement(name = "basicauth")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    public ManagerDto getById(@PathVariable("id") long id) {
        return converter.toDto((managerService.getById(id)));
    }

    @Operation(summary = "Delete manager by ID", description = "Allow to delete an existing manager in the system by ID")
    @SecurityRequirement(name = "basicauth")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    public void deleteById(@PathVariable("id") long id) {
        managerService.deleteById(id);
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