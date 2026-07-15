package com.ebank.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardStatsDTO {
    private long totalCustomers;
    private long totalCurrentAccounts;
    private long totalSavingAccounts;
    private double totalBalance;
    private double totalCredits;
    private double totalDebits;
}
