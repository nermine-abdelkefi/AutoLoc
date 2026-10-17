# Atelier 3 — Repository Notes

## Choix des interfaces Repository

| Interface | Étend | Justification |
|---|---|---|
| IAgenceRepository | JpaRepository<Agence, Long> | CRUD complet, listes, tri et pagination. |
| IEmployeRepository | JpaRepository<Employe, Long> | CRUD complet, listes, tri et pagination. |
| IVehiculeRepository | JpaRepository<Vehicule, Long> | CRUD complet, listes, tri et pagination. |
| IEquipementRepository | JpaRepository<Equipement, Long> | CRUD complet, listes, tri et pagination. |
| IClientRepository | JpaRepository<Client, Long> | CRUD complet, listes, tri et pagination. |
| IReservationRepository | JpaRepository<Reservation, Long> | CRUD complet, listes, tri et pagination. |
| IContratRepository | JpaRepository<Contrat, Long> | CRUD complet, findAll retourne une List, saveAndFlush disponible. |
| IPaiementRepository | JpaRepository<Paiement, Long> | CRUD complet, notamment pour lire les paiements. |
| IMaintenanceRepository | JpaRepository<Maintenance, Long> | CRUD complet, listes, tri et pagination. |

## Analyse SonarQube for IDE

| Anomalie détectée | Règle / explication | Correction apportée |
|---|---|---|
| À compléter après analyse | À compléter | À compléter |
| À compléter après analyse | À compléter | À compléter |
| À compléter après analyse | À compléter | À compléter |