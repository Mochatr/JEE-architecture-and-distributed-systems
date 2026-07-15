package com.ebank.backend.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("SAV")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class SavingAccount extends BankAccount {

    private double interestRate;
}
