import { Customer } from './customer.model';

export type AccountStatus = 'CREATED' | 'ACTIVATED' | 'SUSPENDED';
export type OperationType = 'DEBIT' | 'CREDIT';

export interface BankAccount {
  id: string;
  balance: number;
  createdAt: string;
  status: AccountStatus;
  customerDTO: Customer;
  overDraft?: number;
  interestRate?: number;
  createdBy?: string;
}

export interface AccountOperation {
  id: number;
  operationDate: string;
  amount: number;
  type: OperationType;
  description: string;
  createdBy?: string;
}
