
package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IClientRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final IClientRepository clientRepository;

    @Override
    public Client create(Client client) {

        valider(client);

        return clientRepository.save(client);
    }

    @Override
    public Client findById(Long id) {

        return clientRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Client", id));
    }

    @Override
    public List<Client> findAll() {

        return clientRepository.findAll();
    }

    @Override
    public Client update(Long id, Client client) {

        Client existant = findById(id);

        valider(client);

        existant.setNom(client.getNom());
        existant.setEmail(client.getEmail());

        return clientRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {

        Client existant = findById(id);

        clientRepository.delete(existant);
    }

    private void valider(Client client) {

        if (client.getNom() == null
                || client.getNom().isBlank()) {

            throw new IllegalArgumentException(
                    "Le nom du client est obligatoire");
        }

        if (client.getEmail() == null
                || client.getEmail().isBlank()) {

            throw new IllegalArgumentException(
                    "L'email du client est obligatoire");
        }
    }
}
