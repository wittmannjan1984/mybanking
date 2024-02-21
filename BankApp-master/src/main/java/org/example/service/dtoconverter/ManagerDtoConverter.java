package org.example.service.dtoconverter;

import org.example.entity.Manager;
import org.example.entity.Product;
import org.example.model.dto.ManagerDto;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ManagerDtoConverter implements Converter<ManagerDto, Manager> {

    public ManagerDto toDto(Manager manager) {
        return new ManagerDto(manager.getId(), manager.getFirstName(),
                manager.getLastName(), manager.getStatus(), manager.getEmail(),
                manager.getClients().stream()
                        .map(client -> client.getFirstName() + " " + client.getLastName())
                        .collect(Collectors.toList()),
                manager.getProducts().stream().
                        map(Product::getName).collect(Collectors.toList()),
                manager.getCreatedAt(),
                manager.getUpdatedAt());
    }

    public Manager toEntity(ManagerDto managerDto) {
        return new Manager(managerDto.getFirstName(),
                managerDto.getLastName(), managerDto.getStatus());
    }
}