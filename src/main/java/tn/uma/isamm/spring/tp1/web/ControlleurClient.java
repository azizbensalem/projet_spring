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
import java.util.Optional;
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
        model.addAttribute("type", "Client");
        return "clients";
    }

    @RequestMapping("/user/rechercheClient")
    public String rechercheClient(String nomClient, Model model,  @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "2") int size,
      @RequestParam(name = "errorMessage", defaultValue = "") String errorMessage) {

        Page<Client> listeClients = metierVentes.getClientByNomClient(nomClient, page, size);
        model.addAttribute("activePage", page);
        model.addAttribute("size", size);
        int[] taillePagination = IntStream.range(0, listeClients.getTotalPages()).toArray();
        model.addAttribute("taillePagination", taillePagination);
        model.addAttribute("nbPages", listeClients.getTotalPages());
        model.addAttribute("nbElements", listeClients.getTotalElements());
        model.addAttribute("listeClients", listeClients);
        model.addAttribute("type", "rechercheClient");
        model.addAttribute("input", nomClient);
        return "clients";
    }

    @RequestMapping("/user/rechercheClientVille")
    public String rechercheClientVille(String ville, Model model,  @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "2") int size,
      @RequestParam(name = "errorMessage", defaultValue = "") String errorMessage) {

        Page<Client> listeClients = metierVentes.getClientByVille(ville, page, size);
        model.addAttribute("activePage", page);
        model.addAttribute("size", size);
        int[] taillePagination = IntStream.range(0, listeClients.getTotalPages()).toArray();
        model.addAttribute("taillePagination", taillePagination);
        model.addAttribute("nbPages", listeClients.getTotalPages());
        model.addAttribute("nbElements", listeClients.getTotalElements());
        model.addAttribute("listeClients", listeClients);
        model.addAttribute("type", "rechercheClientVille");
        model.addAttribute("input", ville);
        return "clients";
    }

    @RequestMapping("/user/rechercheClientSort")
    public String rechercheClientSort(String type, Model model, @RequestParam(name = "page", defaultValue = "0") int page,
    @RequestParam(name = "size", defaultValue = "2") int size,
    @RequestParam(name = "errorMessage", defaultValue = "") String errorMessage) {

        if (type.equals("Nom") ) {
            Page<Client> listeClients = metierVentes.getClientsPageableOrderByNomClient(page, size);

            model.addAttribute("activePage", page);
            model.addAttribute("size", size);
            int[] taillePagination = IntStream.range(0, listeClients.getTotalPages()).toArray();
            model.addAttribute("taillePagination", taillePagination);
            model.addAttribute("nbPages", listeClients.getTotalPages());
            model.addAttribute("nbElements", listeClients.getTotalElements());
            model.addAttribute("listeClients", listeClients);
            model.addAttribute("type", "rechercheClientSortNom");
            model.addAttribute("input", type);
            return "clients";
        }
        if (type.equals("Nombre") ) {
            Page<Client> listeClients = metierVentes.getClientsPageableOrderByNumber(page, size);

            model.addAttribute("activePage", page);
            model.addAttribute("size", size);
            int[] taillePagination = IntStream.range(0, listeClients.getTotalPages()).toArray();
            model.addAttribute("taillePagination", taillePagination);
            model.addAttribute("nbPages", listeClients.getTotalPages());
            model.addAttribute("nbElements", listeClients.getTotalElements());
            model.addAttribute("listeClients", listeClients);
            model.addAttribute("type", "rechercheClientSortNombre");
            model.addAttribute("input", type);
            return "clients";
        }
        return "redirect:/user/clients";
    }

    @GetMapping("/admin/clientAjout")
    public String ajoutClient(Model model) {
        model.addAttribute("client", new Client());
        model.addAttribute("detailClient", new DetailClient());
        return "ajouterClient";
    }

    @PostMapping("/admin/clientEnregister")
    public String enregistrerClient(Client c, DetailClient d, Model model) {
        d.setClient(c);
        c.setDetailClient(d);
        metierVentes.saveClient(c);
        return "redirect:/user/clients";
    }

    @GetMapping("/admin/clientSupprimer")
    public String supprimerClient(Long id, Long activePage, Long nbElements, Long size, RedirectAttributes ra) {
        metierVentes.deleteClient(id);
        if(activePage>0 && ((nbElements-1)==(size * (activePage))))
            activePage--;
        ra.addAttribute("page", activePage);
        return "redirect:/user/clients";
    }

    @GetMapping("/admin/clientModifier")
    public String modifierClient(@RequestParam(name="id")Long id, Model model) {
        Client client = metierVentes.getClientById(id);
        model.addAttribute("client",client);
        model.addAttribute("detailClient",client.getDetailClient());
            return "modifierClient";
    }

    @PostMapping("/admin/clientEdit")
    public String editClient(Client client, DetailClient detailClient, Long id) {
        client.setIdClient(id);
        detailClient.setIdDetail(client.getIdClient());
        client.setDetailClient(detailClient);
        detailClient.setClient(client);
        metierVentes.saveClient(client);
        return "redirect:/user/clients";
    }

    @GetMapping("/user/clientDetail")
    public String detailClient(@RequestParam(name="id")Long id, Model model) {
        Client client = metierVentes.getClientById(id);
        model.addAttribute("client",client);
        return "detailClient";
    }

    @GetMapping("/user/clientCommandes")
    public String detailCommandes(@RequestParam(name="id")Long id, Model model) {
        Client client = metierVentes.getClientById(id);
        model.addAttribute("client",client);
        return "commandes";
    }
}
