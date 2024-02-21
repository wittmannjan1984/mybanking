package org.example.service.dtoconverter;

import org.example.entity.Manager;
import org.example.model.dto.ManagerCreateDto;
import org.springframework.stereotype.Service;

@Service
public class ManagerCreateDtoConverter {

    public Manager toEntity(ManagerCreateDto managerCreateDto) {
        return new Manager(managerCreateDto.getFirstName(), managerCreateDto.getLastName(),
                managerCreateDto.getStatus());
    }
}