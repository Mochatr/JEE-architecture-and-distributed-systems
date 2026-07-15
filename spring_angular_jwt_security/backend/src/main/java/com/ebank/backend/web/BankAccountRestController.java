package com.ebank.backend.web;

import com.ebank.backend.dtos.*;
import com.ebank.backend.exceptions.BalanceNotSufficientException;
import com.ebank.backend.exceptions.BankAccountNotFoundException;
import com.ebank.backend.exceptions.CustomerNotFoundException;
import com.ebank.backend.services.BankAccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@Tag(name = "Bank Accounts", description = "Gestion des comptes bancaires et des opérations")
public class BankAccountRestController {

    private final BankAccountService bankAccountService;

    @GetMapping("/accounts")
    public List<BankAccountDTO> bankAccountList() {
        return bankAccountService.bankAccountList();
    }

    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }

    @GetMapping("/customers/{customerId}/accounts")
    public List<BankAccountDTO> customerAccounts(@PathVariable Long customerId) throws CustomerNotFoundException {
        return bankAccountService.customerAccounts(customerId);
    }

    @PostMapping("/customers/{customerId}/accounts/current")
    public CurrentBankAccountDTO saveCurrentBankAccount(@PathVariable Long customerId,
                                                          @RequestParam double initialBalance,
                                                          @RequestParam(defaultValue = "0") double overDraft) throws CustomerNotFoundException {
        return bankAccountService.saveCurrentBankAccount(initialBalance, overDraft, customerId);
    }

    @PostMapping("/customers/{customerId}/accounts/saving")
    public SavingBankAccountDTO saveSavingBankAccount(@PathVariable Long customerId,
                                                        @RequestParam double initialBalance,
                                                        @RequestParam double interestRate) throws CustomerNotFoundException {
        return bankAccountService.saveSavingBankAccount(initialBalance, interestRate, customerId);
    }

    @GetMapping("/accounts/{accountId}/operations")
    public List<AccountOperationDTO> accountHistory(@PathVariable String accountId) {
        return bankAccountService.accountHistory(accountId);
    }

    @PostMapping("/accounts/debit")
    public Map<String, String> debit(@Valid @RequestBody CreditDebitDTO request) throws BankAccountNotFoundException, BalanceNotSufficientException {
        bankAccountService.debit(request.getAccountId(), request.getAmount(), request.getDescription());
        return Map.of("message", "Compte débité avec succès");
    }

    @PostMapping("/accounts/credit")
    public Map<String, String> credit(@Valid @RequestBody CreditDebitDTO request) throws BankAccountNotFoundException {
        bankAccountService.credit(request.getAccountId(), request.getAmount(), request.getDescription());
        return Map.of("message", "Compte crédité avec succès");
    }

    @PostMapping("/accounts/transfer")
    public Map<String, String> transfer(@Valid @RequestBody TransferRequestDTO request) throws BankAccountNotFoundException, BalanceNotSufficientException {
        bankAccountService.transfer(request.getAccountSource(), request.getAccountDestination(), request.getAmount());
        return Map.of("message", "Transfert effectué avec succès");
    }
}
