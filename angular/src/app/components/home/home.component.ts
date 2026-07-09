import { Component } from '@angular/core';
import { NgIf } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [NgIf, RouterLink],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  appName = 'Gestion des produits';
  description = 'Une application Angular de demonstration des concepts de base : composants, data binding, directives, services et routing.';
  logoUrl = 'https://angular.dev/assets/icons/icon-72x72.png';
  isReady = true;
}
