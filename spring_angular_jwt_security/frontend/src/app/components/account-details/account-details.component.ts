import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { AccountOperation, BankAccount } from '../../model/bank-account.model';
import { AccountService } from '../../services/account.service';

@Component({
  selector: 'app-account-details',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './account-details.component.html',
  styleUrl: './account-details.component.css'
})
export class AccountDetailsComponent implements OnInit {
  accountId!: string;
  account?: BankAccount;
  operations: AccountOperation[] = [];
  errorMessage: string = '';
  successMessage: string = '';

  operationAmount: number = 0;
  operationDescription: string = '';

  constructor(private route: ActivatedRoute, private accountService: AccountService) {}

  ngOnInit(): void {
    this.accountId = this.route.snapshot.paramMap.get('accountId')!;
    this.loadAccount();
    this.loadOperations();
  }

  loadAccount(): void {
    this.accountService.getAccount(this.accountId).subscribe((data) => (this.account = data));
  }

  loadOperations(): void {
    this.accountService.getAccountOperations(this.accountId).subscribe({
      next: (data) => (this.operations = data),
      error: (err) => (this.errorMessage = err.message)
    });
  }

  credit(): void {
    this.accountService.credit(this.accountId, this.operationAmount, this.operationDescription).subscribe({
      next: () => this.onOperationSuccess('Compte crédité avec succès'),
      error: (err) => (this.errorMessage = err.error?.message || 'Erreur lors du crédit')
    });
  }

  debit(): void {
    this.accountService.debit(this.accountId, this.operationAmount, this.operationDescription).subscribe({
      next: () => this.onOperationSuccess('Compte débité avec succès'),
      error: (err) => (this.errorMessage = err.error?.message || 'Erreur lors du débit')
    });
  }

  private onOperationSuccess(message: string): void {
    this.successMessage = message;
    this.errorMessage = '';
    this.operationAmount = 0;
    this.operationDescription = '';
    this.loadAccount();
    this.loadOperations();
  }
}
