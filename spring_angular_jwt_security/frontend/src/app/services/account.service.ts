import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { AccountOperation, BankAccount } from '../model/bank-account.model';

@Injectable({ providedIn: 'root' })
export class AccountService {

  constructor(private http: HttpClient) {}

  getAccounts(): Observable<BankAccount[]> {
    return this.http.get<BankAccount[]>(`${environment.backendUrl}/accounts`);
  }

  getAccount(accountId: string): Observable<BankAccount> {
    return this.http.get<BankAccount>(`${environment.backendUrl}/accounts/${accountId}`);
  }

  getAccountOperations(accountId: string): Observable<AccountOperation[]> {
    return this.http.get<AccountOperation[]>(`${environment.backendUrl}/accounts/${accountId}/operations`);
  }

  saveCurrentAccount(customerId: number, initialBalance: number, overDraft: number): Observable<BankAccount> {
    return this.http.post<BankAccount>(
      `${environment.backendUrl}/customers/${customerId}/accounts/current`,
      null,
      { params: { initialBalance, overDraft } }
    );
  }

  saveSavingAccount(customerId: number, initialBalance: number, interestRate: number): Observable<BankAccount> {
    return this.http.post<BankAccount>(
      `${environment.backendUrl}/customers/${customerId}/accounts/saving`,
      null,
      { params: { initialBalance, interestRate } }
    );
  }

  debit(accountId: string, amount: number, description: string): Observable<any> {
    return this.http.post(`${environment.backendUrl}/accounts/debit`, { accountId, amount, description });
  }

  credit(accountId: string, amount: number, description: string): Observable<any> {
    return this.http.post(`${environment.backendUrl}/accounts/credit`, { accountId, amount, description });
  }

  transfer(accountSource: string, accountDestination: string, amount: number): Observable<any> {
    return this.http.post(`${environment.backendUrl}/accounts/transfer`, { accountSource, accountDestination, amount });
  }
}
