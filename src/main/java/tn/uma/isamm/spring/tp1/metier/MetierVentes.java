package tn.uma.isamm.spring.tp1.metier;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import tn.uma.isamm.spring.tp1.entities.*;

public interface MetierVentes {

	// produit
	public List<Produit> getProduits();
	public void saveProduit(Produit p);
	public Produit getProduitById(long id);
	public Page<Produit> getProduitsPageable(int page, int size);
	public List<Categorie> getCategories();
	public void deleteProduit(Long id);

	// client
	public List<Client> getClients();
	public void saveClient(Client c);
	public Client getClientById(long id);
	public Client getClientByNomClient(String nomClient);

	public Page<Client> getClientsPageable(int page, int size);

	public Client getClientByOrderByNomClient();

	public void deleteClient(Long id);

	public AppUser saveAppUser(AppUser appUser);
	public AppRole saveAppRole(AppRole appRole);
	public void addRoleToUser(String login, String nomRole);
	public AppUser getUserByLogin(String login);
	public List<AppUser> getAppUsers();
	
	
	//Page<Produit> getProduitsPageableByDesignation(int page, int size, String mc);
	

	

}
