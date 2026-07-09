import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Product } from '../../models/product';
import { ProductService } from '../../services/product.service';

@Component({
  selector: 'app-products-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './products-list.component.html',
  styleUrl: './products-list.component.css'
})
export class ProductsListComponent implements OnInit {

  products: Product[] = [];
  errorMessage = '';
  keyword = '';

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.productService.getProducts().subscribe({
      next: (products) => this.products = products,
      error: (err) => this.errorMessage = "Impossible de contacter l'API (verifiez que Spring_data tourne sur le port 8080)."
    });
  }

  onSearch(): void {
    if (!this.keyword.trim()) {
      this.loadProducts();
      return;
    }
    this.productService.searchProducts(this.keyword).subscribe({
      next: (products) => this.products = products,
      error: () => this.errorMessage = 'Erreur lors de la recherche.'
    });
  }

  onDelete(id: number | undefined): void {
    if (id === undefined) {
      return;
    }
    if (!confirm('Voulez-vous vraiment supprimer ce produit ?')) {
      return;
    }
    this.productService.deleteProduct(id).subscribe({
      next: () => this.products = this.products.filter(p => p.id !== id),
      error: () => this.errorMessage = 'Erreur lors de la suppression.'
    });
  }
}
