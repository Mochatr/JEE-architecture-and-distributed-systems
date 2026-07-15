import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AccountService } from '../../services/account.service';

@Component({
  selector: 'app-transfer',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './transfer.component.html',
  styleUrl: './transfer.component.css'
})
export class TransferComponent {
  accountSource: string = '';
  accountDestination: string = '';
  amount: number = 0;
  errorMessage: string = '';
  successMessage: string = '';

  constructor(private accountService: AccountService) {}

  transfer(): void {
    this.errorMessage = '';
    this.successMessage = '';
    this.accountService.transfer(this.accountSource, this.accountDestination, this.amount).subscribe({
      next: () => {
        this.successMessage = 'Transfert effectué avec succès';
        this.accountSource = '';
        this.accountDestination = '';
        this.amount = 0;
      },
      error: (err) => (this.errorMessage = err.error?.message || 'Erreur lors du transfert')
    });
  }
}
