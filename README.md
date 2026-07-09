# JEE-architecture-and-distributed-systems

Recueil de projets pratiques autour de l'architecture JEE et des systèmes distribués, chacun illustrant une brique technique de l'écosystème Spring ou de son écosystème front-end.

## Projets

### [Spring_data](Spring_data/README.md)

Gestion de produits avec **Spring Data JPA** et **Hibernate** : couche DAO générée automatiquement, exposée via une API REST.

### [Spring_mvc](Spring_mvc/README.md)

Application Web complète de gestion de produits avec **Spring MVC**, **Thymeleaf** et **Spring Security** : CRUD, recherche, pagination, validation de formulaires et authentification.

### [angular](angular/README.md)

Application front-end **Angular** de gestion de produits, consommant l'API REST de `Spring_data` : composants, data binding, directives, services, routing et formulaires.

## Structure du dépôt

```
.
├── Spring_data/   # Spring Data JPA + API REST
├── Spring_mvc/    # Spring MVC + Thymeleaf + Spring Security
└── angular/       # Angular (front-end consommant l'API de Spring_data)
```

Chaque projet Spring est un module Maven autonome (son propre `pom.xml`, `mvnw`) ; le projet `angular` est un module npm autonome (son propre `package.json`). Se référer au README de chaque projet pour le lancer.
