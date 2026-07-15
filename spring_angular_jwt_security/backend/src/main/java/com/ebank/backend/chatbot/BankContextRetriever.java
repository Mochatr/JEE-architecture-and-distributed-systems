package com.ebank.backend.chatbot;

import com.ebank.backend.dtos.BankAccountDTO;
import com.ebank.backend.dtos.CurrentBankAccountDTO;
import com.ebank.backend.dtos.CustomerDTO;
import com.ebank.backend.dtos.SavingBankAccountDTO;
import com.ebank.backend.services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class BankContextRetriever {

    private final BankAccountService bankAccountService;

    public String retrieveContext(String question) {
        String lowerQuestion = question.toLowerCase(Locale.FRENCH);

        List<CustomerDTO> customers = bankAccountService.listCustomers();
        List<BankAccountDTO> relevantAccounts = bankAccountService.bankAccountList().stream()
                .filter(account -> matchesQuestion(account, lowerQuestion, customers))
                .toList();

        if (relevantAccounts.isEmpty()) {
            relevantAccounts = bankAccountService.bankAccountList();
        }

        return relevantAccounts.stream()
                .map(this::describeAccount)
                .collect(Collectors.joining("\n"));
    }

    private boolean matchesQuestion(BankAccountDTO account, String lowerQuestion, List<CustomerDTO> customers) {
        CustomerDTO customer = customerOf(account);
        return customer != null && lowerQuestion.contains(customer.getName().toLowerCase(Locale.FRENCH));
    }

    private CustomerDTO customerOf(BankAccountDTO account) {
        if (account instanceof CurrentBankAccountDTO cba) return cba.getCustomerDTO();
        if (account instanceof SavingBankAccountDTO sba) return sba.getCustomerDTO();
        return null;
    }

    private String describeAccount(BankAccountDTO account) {
        CustomerDTO customer = customerOf(account);
        if (account instanceof CurrentBankAccountDTO cba) {
            return "Compte courant %s du client %s : solde %.2f DH, découvert autorisé %.2f DH, statut %s."
                    .formatted(cba.getId(), customer.getName(), cba.getBalance(), cba.getOverDraft(), cba.getStatus());
        }
        if (account instanceof SavingBankAccountDTO sba) {
            return "Compte épargne %s du client %s : solde %.2f DH, taux d'intérêt %.2f%%, statut %s."
                    .formatted(sba.getId(), customer.getName(), sba.getBalance(), sba.getInterestRate(), sba.getStatus());
        }
        return "";
    }
}
