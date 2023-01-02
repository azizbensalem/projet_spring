package tn.uma.isamm.spring.tp1.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.uma.isamm.spring.tp1.entities.Client;

import java.util.List;
import java.util.Optional;

public interface ClientDAO extends JpaRepository<Client, Long>{
    public Optional<Client> findByNomClient(String nomClient);
    public Optional<Client> findByOrderByNomClientAsc();

}
