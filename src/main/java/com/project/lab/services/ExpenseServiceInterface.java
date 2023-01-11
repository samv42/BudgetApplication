package com.project.lab.services;

import com.project.lab.models.Account;
import com.project.lab.models.Expense;
import com.project.lab.models.Income;

import java.util.List;

public interface ExpenseServiceInterface {
    List<Expense> getAllExpenses();

    List<Expense> getExpensesByAccount(Account account);

    Expense saveExpense(Expense expense);

    Expense getExpense(Long id);

    void deleteExpense(Long id);

    List<Expense> saveAllExpenses(List<Expense> expenseList);
}
