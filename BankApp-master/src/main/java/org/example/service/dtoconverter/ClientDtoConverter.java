package org.example.service.dtoconverter;

import org.example.entity.Client;
import org.example.model.dto.ClientDto;
import org.springframework.stereotype.Service;

@Service
public class ClientDtoConverter implements Converter<ClientDto, Client> {

    public ClientDto toDto(Client client) {
        return new ClientDto(client.getId(),
                client.getManager() != null ? client.getManager().getFirstName() : null,
                client.getManager() != null ? client.getManager().getLastName() : null,
                client.getStatus(), client.getTaxCode(), client.getFirstName(), client.getLastName(),
                client.getEmail(), client.getAddress(), client.getPhone(),
                client.getCreatedAt(), client.getUpdatedAt());
    }

    public Client toEntity(ClientDto clientDto) {
        return new Client(clientDto.getTaxCode(),
                clientDto.getFirstName(),
                clientDto.getLastName(),
                clientDto.getEmail(),
                clientDto.getAddress(),
                clientDto.getPhone());
    }
}