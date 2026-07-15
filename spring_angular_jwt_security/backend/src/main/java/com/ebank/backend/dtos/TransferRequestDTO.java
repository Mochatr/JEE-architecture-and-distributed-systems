package com.ebank.backend.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class TransferRequestDTO {

    @NotEmpty(message = "Le compte source est obligatoire")
    private String accountSource;

    @NotEmpty(message = "Le compte destination est obligatoire")
    private String accountDestination;

    @Positive(message = "Le montant doit être positif")
    private double amount;
}
