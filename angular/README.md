# angular — Gestion des produits (Angular)

Application front-end **Angular** de gestion de produits, réalisée pour pratiquer les concepts de base d'Angular (Partie 1) : composants, data binding, directives structurelles, services, injection de dépendances, routage et formulaires.

Elle consomme l'API REST du projet [Spring_data](../Spring_data/README.md) (`http://localhost:8080`).

## Stack technique

- Angular 18 (composants standalone)
- Angular Router
- Angular Forms (formulaire template-driven)
- Angular HttpClient
- Bootstrap 5 (CDN, mise en page)

## Concepts pratiqués

- **Composants** : `NavbarComponent`, `HomeComponent`, `ProductsListComponent`, `ProductDetailsComponent`, `AddProductComponent`
- **Data binding** : interpolation (`{{ }}`), property binding (`[src]`, `[routerLink]`), event binding (`(click)`, `(ngSubmit)`), two-way binding (`[(ngModel)]`)
- **Directives structurelles** : `*ngFor`, `*ngIf`
- **Services & injection de dépendances** : `ProductService` (encapsule les appels HTTP)
- **Routing** : `Routes`, `routerLink`, `routerLinkActive`, `ActivatedRoute`, navigation programmatique (`Router.navigate`)
- **Formulaires** : formulaire template-driven avec `ngModel`, validation et affichage des erreurs

## Fonctionnalités

- Liste des produits (avec appel HTTP au chargement)
- Recherche de produits par mot-clé
- Détails d'un produit (route avec paramètre `:id`)
- Ajout d'un produit (formulaire validé)
- Suppression d'un produit

## Lancer le projet

Le projet nécessite que le backend [Spring_data](../Spring_data/README.md) tourne sur `http://localhost:8080` (CORS déjà autorisé pour `http://localhost:4200`).

```bash
npm install
ng serve
```

L'application est accessible sur `http://localhost:4200`.

> Pour un rendu de production (recommandé si le dev-server Vite/esbuild pose problème selon la version de Node installée) :
> ```bash
> ng build
> npx serve -s dist/angular/browser -l 4200
> ```
