package org.example.service;

import org.example.entity.Manager;
import org.example.exception.ManagerExistsException;
import org.example.exception.ManagerNotFoundException;
import org.example.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerRepository managerRepository;

    @Override
    public List<Manager> getAll() {
        return managerRepository.findAll();
    }

    @Override
    public Manager getById(long id) {
        return managerRepository.findById(id)
                .orElseThrow(() -> new ManagerNotFoundException(String.format("Manager with id %d not exists", id)));
    }

    @Override
    public Manager create(Manager manager) {
        Optional<Manager> managerEntity = managerRepository.findByFirstNameAndLastName(manager.getFirstName(), manager.getLastName());
        if (managerEntity.isPresent()) {
            throw new ManagerExistsException("This manager already exists in system");
        }
        return managerRepository.save(manager);
    }

    @Override
    public void deleteById(long id) {
        managerRepository.delete(getById(id));
    }
}