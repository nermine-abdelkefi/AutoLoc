
package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IContratRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;

    @Override
    public Contrat create(Contrat contrat) {

        valider(contrat);

        if (contrat.getIdContrat() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau contrat ne doit pas avoir d'identifiant");
        }

        return contratRepository.save(contrat);
    }

    @Override
    public Contrat findById(Long id) {

        return contratRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Contrat", id));
    }

    @Override
    public List<Contrat> findAll() {

        return contratRepository.findAll();
    }

    @Override
    public Contrat update(Long id, Contrat contrat) {

        Contrat existant = findById(id);

        valider(contrat);

        existant.setDateSignature(contrat.getDateSignature());
        existant.setMontantTotal(contrat.getMontantTotal());
        existant.setValide(contrat.isValide());

        return contratRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {

        Contrat existant = findById(id);

        contratRepository.delete(existant);
    }

    private void valider(Contrat contrat) {

        if (contrat.getMontantTotal() == null
                || contrat.getMontantTotal()
                .compareTo(BigDecimal.ZERO) < 0) {

            throw new IllegalArgumentException(
                    "Le montant total est obligatoire et ne peut pas être négatif");
        }
    }
}
