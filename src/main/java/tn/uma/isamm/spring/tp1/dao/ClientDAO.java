package tn.uma.isamm.spring.tp1.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.uma.isamm.spring.tp1.entities.Client;

public interface ClientDAO extends JpaRepository<Client, Long>{
    @Query("SELECT c FROM Client c WHERE c.nomClient LIKE %:nomClient%")
    public Page<Client> findByNomClient(@Param("nomClient") String nomClient, PageRequest pr);
    public Page<Client> findAllByOrderByNomClientAsc(PageRequest pr);
    @Query("SELECT c FROM Client c LEFT JOIN c.commandes ORDER BY size(c.commandes) DESC")
    public Page<Client> findAllByOrderByCommandesSizeAsc(PageRequest pr);
    public Page<Client> findByDetailClientAdresse(String ville,PageRequest pr);
}
