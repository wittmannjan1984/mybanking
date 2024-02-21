package org.example.service.dtoconverter;

import org.example.entity.Client;
import org.example.model.dto.ClientUpdateDto;
import org.springframework.stereotype.Service;

@Service
public class ClientUpdateDtoConverter {

    public Client toEntity(ClientUpdateDto clientUpdateDto) {
        return new Client(clientUpdateDto.getAddress(),
                clientUpdateDto.getPhone(), clientUpdateDto.getTaxCode());
    }
}