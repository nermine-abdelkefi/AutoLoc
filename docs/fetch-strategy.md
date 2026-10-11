
# Atelier 2 — Associations JPA : Fetch & Cascade
## Projet AutoLoc API

### 1. Introduction

Ce document présente les stratégies de chargement
(FetchType) et de propagation (CascadeType)
utilisées dans les associations JPA du projet AutoLoc.

L'objectif est d'assurer la cohérence des données,
de limiter les chargements inutiles et de respecter
le cycle de vie des entités.

### 2. Stratégies Fetch et Cascade

| Association | Fetch | Cascade | Justification |
|---|---|---|---|
| Contrat → Paiement | LAZY | ALL + orphanRemoval | Un paiement dépend de son contrat. La suppression du contrat entraîne celle de ses paiements. |
| Agence → Vehicule | LAZY | Aucune | Un véhicule peut exister indépendamment de son agence. |
| Agence → Employe | LAZY | Aucune | Un employé ne doit pas être automatiquement supprimé avec son agence. |
| Vehicule ↔ Equipement | LAZY | Aucune | Les équipements peuvent être partagés entre plusieurs véhicules. |
| Client → Reservation | LAZY | PERSIST | Les nouvelles réservations peuvent être enregistrées avec le nouveau client. |
| Reservation → Vehicule | LAZY | Aucune | Une réservation concerne un véhicule existant. |
| Reservation ↔ Contrat | LAZY | ALL côté Reservation | Les opérations sur une réservation sont propagées à son contrat. |
| Vehicule → Maintenance | LAZY | PERSIST | Les nouvelles maintenances peuvent être enregistrées avec le nouveau véhicule. |

### 3. FetchType.LAZY

La stratégie LAZY permet de différer le chargement
des entités associées jusqu'à leur utilisation.

Elle permet de limiter les requêtes inutiles
et de réduire le volume des données chargées.

### 4. CascadeType

- CascadeType.PERSIST : propage l'enregistrement.
- CascadeType.ALL : propage toutes les opérations.
- Aucune cascade : les entités sont gérées séparément.

### 5. orphanRemoval

La propriété orphanRemoval = true est utilisée
dans l'association Contrat → Paiement.

Elle permet de supprimer un paiement de la base
lorsqu'il est retiré de la collection de son contrat.

### 6. Conclusion

Les stratégies choisies permettent d'optimiser
le chargement des données et de respecter
les règles métier du projet AutoLoc.
