package org.example.repository;

import org.example.entity.Account;
import org.example.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    List<Account> findAllByClient(Client client);

    Optional<Account> findByIbanAndClient(String s, Client client);
}