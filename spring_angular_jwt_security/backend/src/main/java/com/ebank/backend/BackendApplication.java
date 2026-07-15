package com.ebank.backend;

import com.ebank.backend.entities.AppRole;
import com.ebank.backend.entities.AppUser;
import com.ebank.backend.entities.Customer;
import com.ebank.backend.repositories.AppRoleRepository;
import com.ebank.backend.repositories.AppUserRepository;
import com.ebank.backend.repositories.CustomerRepository;
import com.ebank.backend.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                             BankAccountService bankAccountService,
                             AppUserRepository appUserRepository,
                             AppRoleRepository appRoleRepository,
                             PasswordEncoder passwordEncoder) {
        return args -> {
            AppRole userRole = appRoleRepository.save(new AppRole(null, "USER"));
            AppRole adminRole = appRoleRepository.save(new AppRole(null, "ADMIN"));

            AppUser admin = new AppUser(null, "admin", passwordEncoder.encode("admin123"), List.of(userRole, adminRole));
            appUserRepository.save(admin);

            AppUser user = new AppUser(null, "user", passwordEncoder.encode("user123"), List.of(userRole));
            appUserRepository.save(user);

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
