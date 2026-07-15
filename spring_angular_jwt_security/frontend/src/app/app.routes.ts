import { Routes } from '@angular/router';
import { CustomersComponent } from './components/customers/customers.component';
import { NewCustomerComponent } from './components/new-customer/new-customer.component';
import { CustomerAccountsComponent } from './components/customer-accounts/customer-accounts.component';
import { AccountDetailsComponent } from './components/account-details/account-details.component';
import { TransferComponent } from './components/transfer/transfer.component';
import { LoginComponent } from './components/login/login.component';
import { ChatbotComponent } from './components/chatbot/chatbot.component';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'customers', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'customers', component: CustomersComponent, canActivate: [authGuard] },
  { path: 'customers/new', component: NewCustomerComponent, canActivate: [authGuard] },
  { path: 'customers/:customerId/accounts', component: CustomerAccountsComponent, canActivate: [authGuard] },
  { path: 'accounts/:accountId', component: AccountDetailsComponent, canActivate: [authGuard] },
  { path: 'transfer', component: TransferComponent, canActivate: [authGuard] },
  { path: 'chatbot', component: ChatbotComponent, canActivate: [authGuard] }
];
