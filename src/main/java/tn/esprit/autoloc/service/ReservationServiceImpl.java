
package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IReservationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;

    @Override
    public Reservation create(Reservation reservation) {

        valider(reservation);

        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation findById(Long id) {

        return reservationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Reservation", id));
    }

    @Override
    public List<Reservation> findAll() {

        return reservationRepository.findAll();
    }

    @Override
    public Reservation update(Long id, Reservation reservation) {

        Reservation existante = findById(id);

        valider(reservation);

        existante.setDateDebut(reservation.getDateDebut());
        existante.setDateFin(reservation.getDateFin());
        existante.setClient(reservation.getClient());
        existante.setVehicule(reservation.getVehicule());

        return reservationRepository.save(existante);
    }

    @Override
    public void deleteById(Long id) {

        Reservation existante = findById(id);

        reservationRepository.delete(existante);
    }

    private void valider(Reservation reservation) {

        if (reservation.getDateDebut() == null
                || reservation.getDateFin() == null) {

            throw new IllegalArgumentException(
                    "Les dates de réservation sont obligatoires");
        }

        if (reservation.getDateFin()
                .isBefore(reservation.getDateDebut())) {

            throw new IllegalArgumentException(
                    "La date de fin doit être postérieure ou égale à la date de début");
        }
    }
}
