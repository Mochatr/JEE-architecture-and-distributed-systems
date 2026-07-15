package com.ebank.backend.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreditDebitDTO {

    @NotEmpty(message = "Le compte est obligatoire")
    private String accountId;

    @Positive(message = "Le montant doit être positif")
    private double amount;

    private String description;
}
