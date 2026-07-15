import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Customer } from '../../model/customer.model';
import { BankAccount } from '../../model/bank-account.model';
import { CustomerService } from '../../services/customer.service';
import { AccountService } from '../../services/account.service';

@Component({
  selector: 'app-customer-accounts',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './customer-accounts.component.html',
  styleUrl: './customer-accounts.component.css'
})
export class CustomerAccountsComponent implements OnInit {
  customerId!: number;
  customer?: Customer;
  accounts: BankAccount[] = [];
  errorMessage: string = '';

  newAccountType: 'current' | 'saving' = 'current';
  initialBalance: number = 0;
  overDraft: number = 0;
  interestRate: number = 0;

  constructor(
    private route: ActivatedRoute,
    private customerService: CustomerService,
    private accountService: AccountService
  ) {}

  ngOnInit(): void {
    this.customerId = Number(this.route.snapshot.paramMap.get('customerId'));
    this.customerService.getCustomer(this.customerId).subscribe((c) => (this.customer = c));
    this.loadAccounts();
  }

  loadAccounts(): void {
    this.customerService.getCustomerAccounts(this.customerId).subscribe({
      next: (data) => (this.accounts = data),
      error: (err) => (this.errorMessage = err.message)
    });
  }

  addAccount(): void {
    const request$ = this.newAccountType === 'current'
      ? this.accountService.saveCurrentAccount(this.customerId, this.initialBalance, this.overDraft)
      : this.accountService.saveSavingAccount(this.customerId, this.initialBalance, this.interestRate);

    request$.subscribe({
      next: () => {
        this.initialBalance = 0;
        this.overDraft = 0;
        this.interestRate = 0;
        this.loadAccounts();
      },
      error: (err) => (this.errorMessage = err.error?.message || 'Erreur lors de la création du compte')
    });
  }
}
