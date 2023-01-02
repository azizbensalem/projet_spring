package tn.uma.isamm.spring.tp1.metier;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.uma.isamm.spring.tp1.dao.*;
import tn.uma.isamm.spring.tp1.entities.*;

@Service
@Transactional
public class MetierVentesImpl implements MetierVentes {
	@Autowired
	ProduitDAO produitDAO;

	@Autowired
	ClientDAO clientDAO;
	
	@Autowired
	CategorieDAO categorieDAO;
	
	@Autowired	
	AppUserDAO appUserDAO;
	
	@Autowired
	AppRoleDAO appRoleDAO;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public List<Produit> getProduits() {
		// TODO Auto-generated method stub
		return produitDAO.findAll();
	}

	@Override
	public void saveProduit(Produit p) {
		// TODO Auto-generated method stub
		produitDAO.save(p);

	}

	@Override
	public Page<Produit> getProduitsPageable(int page, int size) {
		// TODO Auto-generated method stub
		PageRequest pr = PageRequest.of(page, size);
		return produitDAO.findAll(pr);
	}

	@Override
	public Produit getProduitById(long id) {
		// TODO Auto-generated method stub
		Optional<Produit> p = produitDAO.findById(id);
		if (p.isPresent())
			return p.get();
		else
			return null;
	}

	@Override
	public List<Categorie> getCategories() {
		// TODO Auto-generated method stub
		return categorieDAO.findAll();
	}

	@Override
	public void deleteProduit(Long id) {
		// TODO Auto-generated method stub
		produitDAO.deleteById(id);
	}

	@Override
	public List<Client> getClients() {
		// TODO Auto-generated method stub
		return clientDAO.findAll();
	}

	@Override
	public void saveClient(Client p) {
		// TODO Auto-generated method stub
		clientDAO.save(p);
	}

	@Override
	public Page<Client> getClientsPageable(int page, int size) {
		// TODO Auto-generated method stub
		PageRequest pr = PageRequest.of(page, size);
		return clientDAO.findAll(pr);
	}

	@Override
	public Client getClientById(long id) {
		// TODO Auto-generated method stub
		Optional<Client> c = clientDAO.findById(id);
		if (c.isPresent())
			return c.get();
		else
			return null;
	}

	@Override
	public Client getClientByNomClient(String nomClient) {
		// TODO Auto-generated method stub
		Optional<Client> c = clientDAO.findByNomClient(nomClient);
		if (c.isPresent())
			return c.get();
		else
			return null;
	}

	@Override
	public Client getClientByOrderByNomClient() {
		// TODO Auto-generated method stub
		Optional<Client> c = clientDAO.findByOrderByNomClientAsc();
		if (c.isPresent())
			return c.get();
		else
			return null;
	}

	@Override
	public void deleteClient(Long id) {
		// TODO Auto-generated method stub
		clientDAO.deleteById(id);
	}

	@Override
	public AppUser saveAppUser(AppUser appUser) {
		// TODO Auto-generated method stub
		String mdp = appUser.getPassword();
		appUser.setPassword(passwordEncoder.encode(mdp));
		return appUserDAO.save(appUser);
	}

	@Override
	public AppRole saveAppRole(AppRole appRole) {
		// TODO Auto-generated method stub
		return appRoleDAO.save(appRole);
	}

	@Override
	public void addRoleToUser(String login, String nomRole) {
		// TODO Auto-generated method stub
		AppUser user =appUserDAO.findByUsername(login);
		AppRole role =appRoleDAO.findByRoleName(nomRole);
		if(user!=null && role!=null)
			user.getRoles().add(role);
		
		
	}

	@Override
	public AppUser getUserByLogin(String login) {
		// TODO Auto-generated method stub
		return appUserDAO.findByUsername(login);
	}

	@Override
	public List<AppUser> getAppUsers() {
		// TODO Auto-generated method stub
		return appUserDAO.findAll();
	}
	
	

}
