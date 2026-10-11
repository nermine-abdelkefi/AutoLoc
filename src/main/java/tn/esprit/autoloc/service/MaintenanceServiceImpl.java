
package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IMaintenanceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final IMaintenanceRepository maintenanceRepository;

    @Override
    public Maintenance create(Maintenance maintenance) {

        valider(maintenance);

        return maintenanceRepository.save(maintenance);
    }

    @Override
    public Maintenance findById(Long id) {

        return maintenanceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Maintenance", id));
    }

    @Override
    public List<Maintenance> findAll() {

        return maintenanceRepository.findAll();
    }

    @Override
    public Maintenance update(Long id, Maintenance maintenance) {

        Maintenance existante = findById(id);

        valider(maintenance);

        existante.setDateDebut(maintenance.getDateDebut());
        existante.setDateFin(maintenance.getDateFin());
        existante.setVehicule(maintenance.getVehicule());

        return maintenanceRepository.save(existante);
    }

    @Override
    public void deleteById(Long id) {

        Maintenance existante = findById(id);

        maintenanceRepository.delete(existante);
    }

    private void valider(Maintenance maintenance) {

        if (maintenance.getDateDebut() == null
                || maintenance.getDateFin() == null) {

            throw new IllegalArgumentException(
                    "Les dates de maintenance sont obligatoires");
        }

        if (maintenance.getDateFin()
                .isBefore(maintenance.getDateDebut())) {

            throw new IllegalArgumentException(
                    "La date de fin doit être supérieure ou égale à la date de début");
        }
    }
}
