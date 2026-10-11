
package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgenceServiceImpl implements IAgenceService {

    private final IAgenceRepository agenceRepository;

    @Override
    public Agence create(Agence agence) {

        if (agence.getNom() == null || agence.getNom().isBlank()) {
            throw new IllegalArgumentException(
                    "Le nom de l'agence est obligatoire");
        }

        return agenceRepository.save(agence);
    }

    @Override
    public Agence findById(Long id) {

        return agenceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Agence", id));
    }

    @Override
    public List<Agence> findAll() {

        return agenceRepository.findAll();
    }

    @Override
    public Agence update(Long id, Agence agence) {

        Agence existante = findById(id);

        if (agence.getNom() == null || agence.getNom().isBlank()) {
            throw new IllegalArgumentException(
                    "Le nom de l'agence est obligatoire");
        }

        existante.setNom(agence.getNom());

        return agenceRepository.save(existante);
    }

    @Override
    public void deleteById(Long id) {

        Agence existante = findById(id);
        agenceRepository.delete(existante);
    }
}
