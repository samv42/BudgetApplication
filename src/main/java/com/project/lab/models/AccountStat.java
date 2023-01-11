package com.project.lab.models;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AccountStat {
    private double monthlyIncome;
    private double monthlyExpense;
    private int timeToGoal;
}
