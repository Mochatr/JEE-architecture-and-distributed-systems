package com.ebank.backend.mappers;

import com.ebank.backend.dtos.*;
import com.ebank.backend.entities.*;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapper {

    public CustomerDTO fromCustomer(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setCreatedBy(customer.getCreatedBy());
        return customerDTO;
    }

    public Customer fromCustomerDTO(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setId(customerDTO.getId());
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        return customer;
    }

    public CurrentBankAccountDTO fromCurrentAccount(CurrentAccount currentAccount) {
        CurrentBankAccountDTO dto = new CurrentBankAccountDTO();
        dto.setId(currentAccount.getId());
        dto.setBalance(currentAccount.getBalance());
        dto.setCreatedAt(currentAccount.getCreatedAt());
        dto.setStatus(currentAccount.getStatus());
        dto.setOverDraft(currentAccount.getOverDraft());
        dto.setCustomerDTO(fromCustomer(currentAccount.getCustomer()));
        dto.setCreatedBy(currentAccount.getCreatedBy());
        return dto;
    }

    public SavingBankAccountDTO fromSavingAccount(SavingAccount savingAccount) {
        SavingBankAccountDTO dto = new SavingBankAccountDTO();
        dto.setId(savingAccount.getId());
        dto.setBalance(savingAccount.getBalance());
        dto.setCreatedAt(savingAccount.getCreatedAt());
        dto.setStatus(savingAccount.getStatus());
        dto.setInterestRate(savingAccount.getInterestRate());
        dto.setCustomerDTO(fromCustomer(savingAccount.getCustomer()));
        dto.setCreatedBy(savingAccount.getCreatedBy());
        return dto;
    }

    public BankAccountDTO fromBankAccount(BankAccount bankAccount) {
        if (bankAccount instanceof CurrentAccount) {
            return fromCurrentAccount((CurrentAccount) bankAccount);
        } else if (bankAccount instanceof SavingAccount) {
            return fromSavingAccount((SavingAccount) bankAccount);
        }
        throw new IllegalArgumentException("Type de compte inconnu");
    }

    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation) {
        AccountOperationDTO dto = new AccountOperationDTO();
        dto.setId(accountOperation.getId());
        dto.setOperationDate(accountOperation.getOperationDate());
        dto.setAmount(accountOperation.getAmount());
        dto.setType(accountOperation.getType());
        dto.setDescription(accountOperation.getDescription());
        dto.setCreatedBy(accountOperation.getCreatedBy());
        return dto;
    }
}
