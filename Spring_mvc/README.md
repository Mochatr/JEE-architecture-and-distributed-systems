# Spring_mvc — Gestion des produits (Spring MVC)

Application Web JEE basée sur **Spring MVC**, **Spring Data JPA**, **Hibernate**, **Thymeleaf** et **Spring Security**, permettant de gérer des produits (CRUD complet).

## Stack technique

- Spring Boot 3.3
- Spring Web (MVC)
- Spring Data JPA / Hibernate
- Thymeleaf (+ `thymeleaf-extras-springsecurity6`)
- Spring Security
- Spring Validation (Bean Validation)
- Lombok
- H2 (base en mémoire) / MySQL

## Fonctionnalités

- Entité JPA `Product` avec contraintes de validation
- `ProductRepository` (Spring Data JPA), testé via `@DataJpaTest`
- Page template commune (layout Bootstrap via fragments Thymeleaf)
- Liste des produits avec pagination
- Recherche de produits par nom
- Ajout d'un produit avec validation de formulaire
- Édition et mise à jour d'un produit
- Suppression d'un produit
- Sécurisation de l'application avec Spring Security (formulaire de connexion, routes protégées)

## Utilisateurs de test

| Utilisateur | Mot de passe | Rôles        |
|-------------|---------------|--------------|
| `user`      | `1234`        | USER         |
| `admin`     | `1234`        | USER, ADMIN  |

La consultation et la recherche des produits sont publiques ; l'ajout, l'édition et la suppression nécessitent d'être connecté.

## Lancer le projet

```bash
./mvnw spring-boot:run
```

L'application démarre par défaut sur `http://localhost:8080`.

- Console H2 : `http://localhost:8080/h2-console`

## Configuration base de données

Par défaut, le projet utilise une base **H2 en mémoire**. Voir [application.properties](src/main/resources/application.properties) pour basculer vers MySQL.
