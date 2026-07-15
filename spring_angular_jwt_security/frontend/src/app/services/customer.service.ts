import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Customer } from '../model/customer.model';
import { BankAccount } from '../model/bank-account.model';

@Injectable({ providedIn: 'root' })
export class CustomerService {

  constructor(private http: HttpClient) {}

  getCustomers(): Observable<Customer[]> {
    return this.http.get<Customer[]>(`${environment.backendUrl}/customers`);
  }

  searchCustomers(keyword: string): Observable<Customer[]> {
    return this.http.get<Customer[]>(`${environment.backendUrl}/customers/search`, { params: { keyword } });
  }

  getCustomer(id: number): Observable<Customer> {
    return this.http.get<Customer>(`${environment.backendUrl}/customers/${id}`);
  }

  saveCustomer(customer: Customer): Observable<Customer> {
    return this.http.post<Customer>(`${environment.backendUrl}/customers`, customer);
  }

  updateCustomer(id: number, customer: Customer): Observable<Customer> {
    return this.http.put<Customer>(`${environment.backendUrl}/customers/${id}`, customer);
  }

  deleteCustomer(id: number): Observable<void> {
    return this.http.delete<void>(`${environment.backendUrl}/customers/${id}`);
  }

  getCustomerAccounts(customerId: number): Observable<BankAccount[]> {
    return this.http.get<BankAccount[]>(`${environment.backendUrl}/customers/${customerId}/accounts`);
  }
}
