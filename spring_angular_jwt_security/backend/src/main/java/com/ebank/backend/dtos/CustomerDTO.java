package com.ebank.backend.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;

    @NotEmpty(message = "Le nom du client est obligatoire")
    private String name;

    @Email(message = "L'email n'est pas valide")
    @NotEmpty(message = "L'email est obligatoire")
    private String email;

    private String createdBy;
}
