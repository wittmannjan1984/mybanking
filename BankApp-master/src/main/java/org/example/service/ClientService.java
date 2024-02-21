package org.example.service;

import org.example.entity.Client;

import java.util.List;

public interface ClientService {

    List<Client> getAll();

    Client getById(long id);

    Client create(Client client);

    void deleteById(long id);

    Client update(Client client);

    Client getCurrent();
}