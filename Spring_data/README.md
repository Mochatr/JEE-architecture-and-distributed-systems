# Spring_data — Gestion des produits (Spring Data JPA)

Projet Spring Boot illustrant l'accès aux données avec **Spring Data JPA** et **Hibernate**, sans couche web/vues.

## Objectif

Manipuler la couche DAO (Data Access Object) générée automatiquement par Spring Data JPA à partir d'une simple interface `Repository`, et exposer les produits via une API REST.

## Stack technique

- Spring Boot 3.5
- Spring Data JPA / Hibernate
- Spring Web (API REST)
- H2 (base en mémoire) / MySQL
- Lombok
- SpringDoc OpenAPI (documentation Swagger)

## Fonctionnalités

- Entité JPA `Product`
- `ProductRepository` (interface `JpaRepository`)
- Ajout de produits via plusieurs méthodes (repository direct, `CommandLineRunner`, `ApplicationRunner`)
- Listing des produits
- Suppression de produits
- API REST (`ProductRestController`)

## Lancer le projet

```bash
./mvnw spring-boot:run
```

L'application démarre par défaut sur `http://localhost:8080`.

- Console H2 : `http://localhost:8080/h2-console`
- Documentation Swagger : `http://localhost:8080/swagger-ui.html`

## Configuration base de données

Par défaut, le projet utilise une base **H2 en mémoire**. Voir [application.properties](src/main/resources/application.properties) pour basculer vers MySQL.
