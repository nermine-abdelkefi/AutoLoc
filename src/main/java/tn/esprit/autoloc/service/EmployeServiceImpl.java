
package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IEmployeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService {

    private final IEmployeRepository employeRepository;

    @Override
    public Employe create(Employe employe) {

        if (employe.getNom() == null || employe.getNom().isBlank()) {
            throw new IllegalArgumentException(
                    "Le nom de l'employé est obligatoire");
        }

        return employeRepository.save(employe);
    }

    @Override
    public Employe findById(Long id) {

        return employeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employe", id));
    }

    @Override
    public List<Employe> findAll() {

        return employeRepository.findAll();
    }

    @Override
    public Employe update(Long id, Employe employe) {

        Employe existant = findById(id);

        if (employe.getNom() == null || employe.getNom().isBlank()) {
            throw new IllegalArgumentException(
                    "Le nom de l'employé est obligatoire");
        }

        existant.setNom(employe.getNom());

        return employeRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {

        Employe existant = findById(id);
        employeRepository.delete(existant);
    }
}
