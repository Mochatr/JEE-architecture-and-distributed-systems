package com.ebank.backend.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ChatRequestDTO {

    @NotEmpty(message = "La question ne peut pas être vide")
    private String question;
}
