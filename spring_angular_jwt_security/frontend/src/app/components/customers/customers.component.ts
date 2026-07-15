import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Customer } from '../../model/customer.model';
import { CustomerService } from '../../services/customer.service';

@Component({
  selector: 'app-customers',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './customers.component.html',
  styleUrl: './customers.component.css'
})
export class CustomersComponent implements OnInit {
  customers: Customer[] = [];
  keyword: string = '';
  errorMessage: string = '';

  constructor(private customerService: CustomerService) {}

  ngOnInit(): void {
    this.loadCustomers();
  }

  loadCustomers(): void {
    this.customerService.getCustomers().subscribe({
      next: (data) => (this.customers = data),
      error: (err) => (this.errorMessage = err.message)
    });
  }

  search(): void {
    if (!this.keyword.trim()) {
      this.loadCustomers();
      return;
    }
    this.customerService.searchCustomers(this.keyword).subscribe({
      next: (data) => (this.customers = data),
      error: (err) => (this.errorMessage = err.message)
    });
  }

  deleteCustomer(customer: Customer): void {
    if (!customer.id || !confirm(`Supprimer le client ${customer.name} ?`)) return;
    this.customerService.deleteCustomer(customer.id).subscribe({
      next: () => this.loadCustomers(),
      error: (err) => (this.errorMessage = err.message)
    });
  }
}
