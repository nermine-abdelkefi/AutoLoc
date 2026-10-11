
# Atelier 4 — Inversion de contrôle, injection de dépendances et couche Service

**Projet :** AutoLoc API  
**Technologies :** Java 17, Spring Boot, Spring Data JPA, Hibernate, Lombok et MySQL

## A. Inversion de contrôle (IoC)

L'inversion de contrôle (IoC) est un principe selon lequel la gestion des objets est confiée au framework Spring plutôt qu'au développeur.

Dans notre projet AutoLoc, Spring crée et gère automatiquement les objets appelés beans.

L'annotation `@Service` indique à Spring qu'une classe appartient à la couche métier et doit être gérée par son conteneur IoC.

Par exemple, `ContratServiceImpl` est automatiquement détectée grâce à cette annotation.

Cette approche permet de réduire le couplage entre les différentes classes et de faciliter la maintenance du projet.

## B. Injection de dépendances

L'injection de dépendances (DI) est un mécanisme permettant à une classe de recevoir les objets dont elle a besoin sans les créer elle-même.

Dans notre projet, les services utilisent les repositories pour communiquer avec la base de données.

### B.1. Injection par attribut

Nous avons d'abord expérimenté l'injection par attribut avec l'annotation `@Autowired`.

Exemple :

```java
@Service
public class ContratServiceImpl implements IContratService {

    @Autowired
    private IContratRepository contratRepository;
}
```

Cette méthode fonctionne, mais elle rend les dépendances moins explicites et complique certains tests unitaires.

### B.2. Injection par constructeur

Nous avons ensuite utilisé l'injection par constructeur avec Lombok.

Exemple :

```java
@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;
}
```

L'annotation `@RequiredArgsConstructor` génère automatiquement un constructeur contenant les attributs `final`.

Spring utilise ce constructeur pour injecter le repository.

### B.3. Comparaison des deux méthodes

| Critère | Injection par attribut | Injection par constructeur |
|---|---|---|
| Annotation principale | @Autowired | @RequiredArgsConstructor |
| Dépendances explicites | Non | Oui |
| Utilisation de final | Non | Oui |
| Facilité des tests | Moins pratique | Plus pratique |
| Recommandation | Moins recommandée | Recommandée |

**Choix retenu :** l'injection par constructeur.

Cette méthode rend le code plus clair, facilite les tests et permet de garantir que les dépendances sont fournies lors de la création de l'objet.

## C. Couche Service et gestion des erreurs

### C.1. Organisation de la couche Service

La couche Service contient les traitements métier de l'application AutoLoc.

Nous avons créé neuf interfaces Service et neuf classes d'implémentation.

| Interface | Implémentation |
|---|---|
| IAgenceService | AgenceServiceImpl |
| IClientService | ClientServiceImpl |
| IContratService | ContratServiceImpl |
| IEmployeService | EmployeServiceImpl |
| IEquipementService | EquipementServiceImpl |
| IMaintenanceService | MaintenanceServiceImpl |
| IPaiementService | PaiementServiceImpl |
| IReservationService | ReservationServiceImpl |
| IVehiculeService | VehiculeServiceImpl |

Chaque interface définit les méthodes disponibles pour une entité.

Les classes d'implémentation contiennent leur fonctionnement et utilisent les repositories pour accéder aux données.

### C.2. Opérations CRUD

Les services proposent généralement cinq opérations :

- `create()` : ajouter une nouvelle entité.
- `findById()` : rechercher une entité par son identifiant.
- `findAll()` : récupérer toutes les entités.
- `update()` : modifier une entité existante.
- `deleteById()` : supprimer une entité.

Le service Paiement est une exception : dans cet atelier, il propose uniquement `findById()` et `findAll()`.

### C.3. Gestion des erreurs

Nous avons créé une exception personnalisée appelée `ResourceNotFoundException`.

Elle hérite de `RuntimeException`.

Lorsqu'une entité est introuvable, la couche Service déclenche cette exception.

Exemple :

```java
return contratRepository.findById(id)
        .orElseThrow(() ->
            new ResourceNotFoundException("Contrat", id));
```

Si aucun contrat ne correspond à l'identifiant demandé, une exception est déclenchée avec un message explicite.

Cette approche permet d'éviter de retourner silencieusement une valeur inexistante.

### C.4. Validations métier

Nous avons ajouté des vérifications dans les services pour assurer la cohérence des données.

Les principales règles sont :

- Agence : le nom est obligatoire.
- Employe : le nom est obligatoire.
- Vehicule : le tarif journalier doit être positif ou nul.
- Equipement : le libellé est obligatoire.
- Client : le nom et l'email sont obligatoires.
- Reservation : la date de fin doit être supérieure ou égale à la date de début.
- Contrat : le montant total doit être positif ou nul.
- Maintenance : la date de fin doit être supérieure ou égale à la date de début.
- Paiement : les opérations sont limitées à la lecture.

Ces validations permettent d'empêcher certaines données incorrectes d'être enregistrées dans la base.

## D. Analyse SonarQube for IDE

### D.1. Installation et configuration

Nous avons installé le plugin `SonarQube for IDE` dans IntelliJ IDEA à partir du Marketplace.

Après l'installation, nous avons redémarré IntelliJ afin d'activer le plugin.

L'analyse automatique est activée.

### D.2. Analyse du projet

Nous avons lancé une analyse sur le package :

`tn.esprit.autoloc`

Ce package contient les entités, les repositories, les services et les autres composants de l'application.

Dans la fenêtre Findings, les messages suivants sont affichés :

- `No Issues`
- `No findings to display`
- `Automatic analysis is enabled`

Aucune anomalie n'était affichée dans la vue consultée au moment de la vérification.

### D.3. Trois observations

**Observation 1 — Installation du plugin**

SonarQube for IDE a été installé et activé correctement dans IntelliJ IDEA.

**Observation 2 — Analyse automatique**

L'analyse automatique est activée et permet de contrôler la qualité du code pendant le développement.

**Observation 3 — Résultat affiché**

La fenêtre Findings indique `No Issues`. Aucun problème n'est actuellement présenté dans cette vue.

Ces observations concernent le fonctionnement du plugin et le résultat visible de l'analyse. Elles ne représentent pas trois anomalies de code détectées.

### D.4. Améliorations de qualité appliquées

Lors du développement, nous avons également amélioré plusieurs éléments du code :

- Remplacement de l'injection par attribut par l'injection par constructeur.
- Utilisation des attributs `private final` pour les repositories.
- Centralisation de certaines validations dans des méthodes privées.
- Utilisation de `ResourceNotFoundException` pour les ressources inexistantes.

Ces modifications contribuent à améliorer la lisibilité et la maintenabilité des services.

## E. Bilan et vérifications

### E.1. Vérification de la compilation

Nous avons exécuté la commande suivante à la racine du projet :

```powershell
.\mvnw.cmd clean compile
```

La compilation Maven s'est terminée avec le résultat :

```text
Compiling 43 source files with javac
BUILD SUCCESS
```

Cela confirme que les fichiers Java du projet compilent sans erreur.

### E.2. Vérification de Spring Boot

Le démarrage et le test de chargement du contexte Spring ont permis de vérifier les éléments suivants :

- Détection des neuf repositories JPA.
- Initialisation d'Hibernate.
- Connexion à la base `autoloc_db` sur le port `3307`.
- Chargement du contexte Spring Boot.
- Exécution réussie du test `contextLoads`.

Un avertissement de compatibilité lié à la version de la base détectée par Hibernate reste présent, sans bloquer les vérifications réalisées.

### E.3. Résultat final

Cet atelier nous a permis de comprendre le rôle de l'inversion de contrôle et de l'injection de dépendances dans Spring Boot.

Nous avons mis en place une couche Service structurée, composée d'interfaces et de classes d'implémentation.

Nous avons également ajouté des règles de validation métier et une exception personnalisée pour gérer les ressources introuvables.

Enfin, la compilation Maven et le chargement du contexte Spring ont réussi.

La couche Service est donc prête pour la suite du projet, notamment la création des contrôleurs REST et les tests fonctionnels des opérations CRUD.
