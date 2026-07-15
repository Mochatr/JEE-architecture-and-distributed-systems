import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CustomerService } from '../../services/customer.service';

@Component({
  selector: 'app-new-customer',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './new-customer.component.html',
  styleUrl: './new-customer.component.css'
})
export class NewCustomerComponent {
  form: FormGroup;
  errorMessage: string = '';

  constructor(private fb: FormBuilder, private customerService: CustomerService, private router: Router) {
    this.form = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]]
    });
  }

  save(): void {
    if (this.form.invalid) return;
    this.customerService.saveCustomer(this.form.value).subscribe({
      next: () => this.router.navigateByUrl('/customers'),
      error: (err) => (this.errorMessage = err.error?.message || 'Erreur lors de la création du client')
    });
  }
}
