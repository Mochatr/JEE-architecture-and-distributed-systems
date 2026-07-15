package com.ebank.backend;

import com.ebank.backend.entities.Customer;
import com.ebank.backend.repositories.CustomerRepository;
import com.ebank.backend.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CustomerRepository customerRepository, BankAccountService bankAccountService) {
        return args -> {
            Stream.of("Hassan", "Imane", "Mohamed").forEach(name -> {
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name.toLowerCase() + "@ebank.com");
                customerRepository.save(customer);
            });

            customerRepository.findAll().forEach(customer -> {
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random() * 90000, 9000, customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random() * 120000, 5.5, customer.getId());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            });

            bankAccountService.bankAccountList().forEach(bankAccountDTO -> {
                String accountId = bankAccountDTO instanceof com.ebank.backend.dtos.CurrentBankAccountDTO cba
                        ? cba.getId()
                        : ((com.ebank.backend.dtos.SavingBankAccountDTO) bankAccountDTO).getId();
                try {
                    bankAccountService.credit(accountId, 10000, "Crédit initial");
                    bankAccountService.debit(accountId, 3000, "Retrait guichet");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            });
        };
    }
}
