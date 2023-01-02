package tn.uma.isamm.spring.tp1.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tn.uma.isamm.spring.tp1.entities.Client;
import tn.uma.isamm.spring.tp1.entities.DetailClient;
import tn.uma.isamm.spring.tp1.metier.MetierVentes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Controller
public class ControlleurClient {
    @Autowired
    private MetierVentes metierVentes;

    @RequestMapping("/user/clients")
    public String clients(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "2") int size,
                           @RequestParam(name = "errorMessage", defaultValue = "") String errorMessage) {
        Page<Client> listeClients = metierVentes.getClientsPageable(page, size);

        model.addAttribute("activePage", page);
        model.addAttribute("size", size);
        int[] taillePagination = IntStream.range(0, listeClients.getTotalPages()).toArray();
        model.addAttribute("taillePagination", taillePagination);
        model.addAttribute("nbPages", listeClients.getTotalPages());
        model.addAttribute("nbElements", listeClients.getTotalElements());
        model.addAttribute("listeClients", listeClients);
        return "clients";
    }

    @PostMapping("/user/rechercheClient")
    public String rechercheClient(long id, Model model) {
        Client client = metierVentes.getClientById(id);
        boolean etat = true;
        if (client == null)
            etat = false;
        else {
            ArrayList<Client> clients = new ArrayList<Client>();
            clients.add(client);
            model.addAttribute("activePage", 0);
            model.addAttribute("size", 2);
            model.addAttribute("taillePagination", 0);
            model.addAttribute("listeClients", clients);
            model.addAttribute("etat", etat);
        }
        return "clients";
    }

    @GetMapping("/admin/client/ajout")
    public String ajoutClient(Model model) {
        model.addAttribute("client", new Client());
        model.addAttribute("detailClient", new DetailClient());
        return "ajouterClient";
    }

    @PostMapping("/admin/client/enregister")
    public String enregistrerClient(Client c, DetailClient d, Model model) {
        d.setClient(c);
        c.setDetailClient(d);
        metierVentes.saveClient(c);
        return "redirect:/user/clients";
    }

    @GetMapping("/admin/client/supprimer")
    public String supprimerClient(Long id, Long activePage, Long nbElements, Long size, RedirectAttributes ra) {
        metierVentes.deleteClient(id);
        if(activePage>0 && ((nbElements-1)==(size * (activePage))))
            activePage--;
        ra.addAttribute("page", activePage);
        return "redirect:/user/clients";
    }

    @GetMapping("/admin/client/modifier")
    public String modifierClient(@RequestParam(name="id")Long id, Model model) {
        Client client = metierVentes.getClientById(id);
        model.addAttribute("client",client);
            return "modifierClient";
    }

    @PostMapping("/admin/client/edit")
    public String editClient(Client client, Long id) {
        client.setIdClient(id);
        metierVentes.saveClient(client);
        return "redirect:/user/clients";
    }

    @GetMapping("/admin/client/detail")
    public String detailClient(@RequestParam(name="id")Long id, Model model) {
        Client client = metierVentes.getClientById(id);
        model.addAttribute("client",client);
        return "detailClient";
    }

    @GetMapping("/admin/client/commandes")
    public String detailCommandes(@RequestParam(name="id")Long id, Model model) {
        Client client = metierVentes.getClientById(id);
        model.addAttribute("client",client);
        return "commandes";
    }
}
