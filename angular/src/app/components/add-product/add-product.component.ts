import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Product } from '../../models/product';
import { ProductService } from '../../services/product.service';

@Component({
  selector: 'app-add-product',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './add-product.component.html',
  styleUrl: './add-product.component.css'
})
export class AddProductComponent {

  product: Product = {
    name: '',
    description: '',
    price: 0,
    quantity: 0
  };

  errorMessage = '';

  constructor(
    private productService: ProductService,
    private router: Router
  ) { }

  onSubmit(form: NgForm): void {
    if (form.invalid) {
      return;
    }
    this.productService.addProduct(this.product).subscribe({
      next: () => this.router.navigate(['/products']),
      error: () => this.errorMessage = "Erreur lors de l'ajout du produit."
    });
  }
}
