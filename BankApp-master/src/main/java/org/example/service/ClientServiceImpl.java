package org.example.service;

import org.example.entity.Client;

import org.example.exception.ClientExistsException;
import org.example.exception.ClientNotFoundException;
import org.example.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private UserService userService;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client getById(long id) {
        return clientRepository.findById(id).orElseThrow(
                () -> new ClientNotFoundException(String.format("Client with id %d not found", id)));
    }

    @Override
    public Client create(Client client) {
        Optional<Client> clientEntity = clientRepository.findClientByEmail(client.getEmail());
        if (clientEntity.isPresent()) {
            throw new ClientExistsException("This client already exists in system");
        }
        client.setEmail(userService.getCurrentUserLogin());
        userService.updateRoleToClient(userService.getCurrentUserLogin());
        return clientRepository.save(client);
    }

    @Override
    public void deleteById(long id) {
        clientRepository.delete(getById(id));
    }

    @Override
    public Client update(Client client) {
        String login = userService.getCurrentUserLogin();
        Client clientEntity = clientRepository.findClientByEmail(login)
                .orElseThrow(() -> new ClientNotFoundException(String.format("Client with login %S not found", login)));

        if (client.getAddress() != null) {
            clientEntity.setAddress(client.getAddress());
        }
        if (client.getPhone() != null) {
            clientEntity.setPhone(client.getPhone());
        }
        if (client.getTaxCode() != null) {
            clientEntity.setTaxCode(client.getTaxCode());
        }
        clientEntity.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return clientRepository.save(clientEntity);
    }

    @Override
    public Client getCurrent() {
        return clientRepository.findClientByEmail(userService.getCurrentUserLogin())
                .orElseThrow(() -> new ClientNotFoundException("Please fill the personal information"));
    }
}