import { Routes } from '@angular/router';
import { CustomersComponent } from './components/customers/customers.component';
import { NewCustomerComponent } from './components/new-customer/new-customer.component';
import { CustomerAccountsComponent } from './components/customer-accounts/customer-accounts.component';
import { AccountDetailsComponent } from './components/account-details/account-details.component';
import { TransferComponent } from './components/transfer/transfer.component';

export const routes: Routes = [
  { path: '', redirectTo: 'customers', pathMatch: 'full' },
  { path: 'customers', component: CustomersComponent },
  { path: 'customers/new', component: NewCustomerComponent },
  { path: 'customers/:customerId/accounts', component: CustomerAccountsComponent },
  { path: 'accounts/:accountId', component: AccountDetailsComponent },
  { path: 'transfer', component: TransferComponent }
];
