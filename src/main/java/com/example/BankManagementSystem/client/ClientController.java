package com.example.BankManagementSystem.client;

import com.example.BankManagementSystem.utils.secretKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/client")
public class ClientController {

    @Autowired
    private final ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping(path = "/all")
    public List<Client> getClients(){
        System.out.println("This works");
        return clientRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Client getClient(@PathVariable long id){
            try {
                Optional<Client> optionalClient = clientRepository.findById(id);
                if (optionalClient.isPresent()){
                    return optionalClient.get();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        return null;
    }

    @PostMapping
    public String newClient(@RequestBody Client client){
        client.setPassword(secretKey.encryptPassword(client.getPassword()));
         clientRepository.save(client);
        return "Saved";
    }
    @PutMapping(path = "/{id}")
    public String updateClient(Client client, @PathVariable long id){
       Optional<Client> clientToUpdate = clientRepository.findById(id);
       if(clientToUpdate.isPresent()){
            clientRepository.findById(id).map(updatedclient -> {
                 updatedclient.setName(client.getName());
                 updatedclient.setAdress(client.getAdress());
                 updatedclient.setEmail(client.getEmail());
                 updatedclient.setDateOfBirth(client.getDateOfBirth());
                 return clientRepository.save(updatedclient);
            });
       }else {
           return "Client not found";
       }
       return "Updated";
    }

    @DeleteMapping(path = "/{id}")
    public String deleteClient(@PathVariable long id ){
        clientRepository.deleteById(id);
        return "Deleted";
    }

}
