package org.example.service;

import org.example.entity.Manager;

import java.util.List;

public interface ManagerService {

    List<Manager> getAll();

    Manager getById(long id);

    Manager create(Manager manager);

    void deleteById(long id);
}