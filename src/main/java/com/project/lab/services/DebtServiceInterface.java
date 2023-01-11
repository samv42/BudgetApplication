package com.project.lab.services;

import com.project.lab.models.Account;
import com.project.lab.models.Debt;

import java.util.List;

public interface DebtServiceInterface {
    List<Debt> getAllDebts();

    List<Debt> getDebtsByAccount(Account account);

    Debt saveDebt(Debt debt);

    Debt getDebt(Long id);

    void deleteDebt(Long id);

    List<Debt> saveAllDebts(List<Debt> debtList);
}
