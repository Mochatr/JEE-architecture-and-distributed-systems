package com.ebank.backend.web;

import com.ebank.backend.dtos.CustomerDTO;
import com.ebank.backend.exceptions.CustomerNotFoundException;
import com.ebank.backend.services.BankAccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@Tag(name = "Customers", description = "Gestion des clients")
public class CustomerRestController {

    private final BankAccountService bankAccountService;

    @GetMapping("/customers")
    public List<CustomerDTO> customers() {
        return bankAccountService.listCustomers();
    }

    @GetMapping("/customers/search")
    public List<CustomerDTO> searchCustomers(@RequestParam(name = "keyword", defaultValue = "") String keyword) {
        return bankAccountService.searchCustomers(keyword);
    }

    @GetMapping("/customers/{customerId}")
    public CustomerDTO getCustomer(@PathVariable Long customerId) throws CustomerNotFoundException {
        return bankAccountService.getCustomer(customerId);
    }

    @PostMapping("/customers")
    public CustomerDTO saveCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        return bankAccountService.saveCustomer(customerDTO);
    }

    @PutMapping("/customers/{customerId}")
    public CustomerDTO updateCustomer(@PathVariable Long customerId, @Valid @RequestBody CustomerDTO customerDTO) throws CustomerNotFoundException {
        return bankAccountService.updateCustomer(customerId, customerDTO);
    }

    @DeleteMapping("/customers/{customerId}")
    public void deleteCustomer(@PathVariable Long customerId) {
        bankAccountService.deleteCustomer(customerId);
    }
}
