package com.ebank.backend.services;

import com.ebank.backend.dtos.*;
import com.ebank.backend.entities.*;
import com.ebank.backend.enums.AccountStatus;
import com.ebank.backend.enums.OperationType;
import com.ebank.backend.exceptions.BalanceNotSufficientException;
import com.ebank.backend.exceptions.BankAccountNotFoundException;
import com.ebank.backend.exceptions.CustomerNotFoundException;
import com.ebank.backend.mappers.BankAccountMapper;
import com.ebank.backend.repositories.AccountOperationRepository;
import com.ebank.backend.repositories.BankAccountRepository;
import com.ebank.backend.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class BankAccountServiceImpl implements BankAccountService {

    private final CustomerRepository customerRepository;
    private final BankAccountRepository bankAccountRepository;
    private final AccountOperationRepository accountOperationRepository;
    private final BankAccountMapper bankAccountMapper;

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = bankAccountMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return bankAccountMapper.fromCustomer(savedCustomer);
    }

    @Override
    public CustomerDTO updateCustomer(Long customerId, CustomerDTO customerDTO) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Client introuvable"));
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        return bankAccountMapper.fromCustomer(customerRepository.save(customer));
    }

    @Override
    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public List<CustomerDTO> listCustomers() {
        return customerRepository.findAll().stream()
                .map(bankAccountMapper::fromCustomer)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerDTO> searchCustomers(String keyword) {
        return customerRepository.findByNameContainingIgnoreCase(keyword).stream()
                .map(bankAccountMapper::fromCustomer)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Client introuvable"));
        return bankAccountMapper.fromCustomer(customer);
    }

    @Override
    public CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Client introuvable"));

        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(initialBalance);
        currentAccount.setOverDraft(overDraft);
        currentAccount.setStatus(AccountStatus.CREATED);
        currentAccount.setCustomer(customer);

        return bankAccountMapper.fromCurrentAccount(bankAccountRepository.save(currentAccount));
    }

    @Override
    public SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Client introuvable"));

        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setInterestRate(interestRate);
        savingAccount.setStatus(AccountStatus.CREATED);
        savingAccount.setCustomer(customer);

        return bankAccountMapper.fromSavingAccount(bankAccountRepository.save(savingAccount));
    }

    @Override
    public List<BankAccountDTO> bankAccountList() {
        return bankAccountRepository.findAll().stream()
                .map(bankAccountMapper::fromBankAccount)
                .collect(Collectors.toList());
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException("Compte bancaire introuvable"));
        return bankAccountMapper.fromBankAccount(bankAccount);
    }

    @Override
    public List<BankAccountDTO> customerAccounts(Long customerId) throws CustomerNotFoundException {
        if (!customerRepository.existsById(customerId)) {
            throw new CustomerNotFoundException("Client introuvable");
        }
        return bankAccountRepository.findAll().stream()
                .filter(bankAccount -> bankAccount.getCustomer().getId().equals(customerId))
                .map(bankAccountMapper::fromBankAccount)
                .collect(Collectors.toList());
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException("Compte bancaire introuvable"));
        if (bankAccount.getBalance() < amount) {
            throw new BalanceNotSufficientException("Solde insuffisant");
        }

        AccountOperation operation = new AccountOperation();
        operation.setOperationDate(new Date());
        operation.setAmount(amount);
        operation.setType(OperationType.DEBIT);
        operation.setDescription(description);
        operation.setBankAccount(bankAccount);
        accountOperationRepository.save(operation);

        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException("Compte bancaire introuvable"));

        AccountOperation operation = new AccountOperation();
        operation.setOperationDate(new Date());
        operation.setAmount(amount);
        operation.setType(OperationType.CREDIT);
        operation.setDescription(description);
        operation.setBankAccount(bankAccount);
        accountOperationRepository.save(operation);

        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String accountSourceId, String accountDestinationId, double amount)
            throws BankAccountNotFoundException, BalanceNotSufficientException {
        debit(accountSourceId, amount, "Transfert vers " + accountDestinationId);
        credit(accountDestinationId, amount, "Transfert depuis " + accountSourceId);
    }

    @Override
    public List<AccountOperationDTO> accountHistory(String accountId) {
        return accountOperationRepository.findByBankAccountIdOrderByOperationDateDesc(accountId, PageRequest.of(0, 100))
                .stream()
                .map(bankAccountMapper::fromAccountOperation)
                .collect(Collectors.toList());
    }
}
