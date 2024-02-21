package org.example.service.dtoconverter;

import org.example.entity.Client;
import org.example.model.dto.ClientCreateDto;
import org.springframework.stereotype.Service;

@Service
public class ClientCreateDtoConverter {

    public Client toEntity(ClientCreateDto clientCreateDto) {
        return new Client(clientCreateDto.getFirstName(), clientCreateDto.getLastName(),
                clientCreateDto.getEmail(), clientCreateDto.getAddress(), clientCreateDto.getPhone());
    }
}