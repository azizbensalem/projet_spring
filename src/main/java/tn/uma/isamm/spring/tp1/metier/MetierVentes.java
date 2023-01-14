package tn.uma.isamm.spring.tp1.metier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	public List<Client> getClients();
	public void saveClient(Client c);
	public Client getClientById(long id);

	public Page<Client> getClientsPageable(int page, int size);

	Page<Client> getClientByNomClient(String nomClient, int page, int size);

	Page<Client> getClientByVille(String ville, int page, int size);
	Page<Client> getClientsPageableOrderByNomClient(int page, int size);

	Page<Client> getClientsPageableOrderByNumber(int page, int size);

	public void deleteClient(Long id);

	public AppUser saveAppUser(AppUser appUser);
	public AppRole saveAppRole(AppRole appRole);
	public void addRoleToUser(String login, String nomRole);
	public AppUser getUserByLogin(String login);
	public List<AppUser> getAppUsers();
	
	


	

}
