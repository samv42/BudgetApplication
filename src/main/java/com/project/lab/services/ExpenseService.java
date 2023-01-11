package com.project.lab.services;

import com.project.lab.models.Account;
import com.project.lab.models.Expense;
import com.project.lab.repo.ExpenseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class ExpenseService implements ExpenseServiceInterface{
    @Autowired
    ExpenseRepo expenseRepo;

    @Override
    public List<Expense> getAllExpenses() {
        return expenseRepo.findAll();
    }

    @Override
    public List <Expense> getExpensesByAccount(Account account){return expenseRepo.getAllExpensesByAccount(account);}

    @Override
    @Transactional
    public Expense saveExpense(Expense expense) {
        return expenseRepo.save(expense);
    }

    @Override
    public Expense getExpense(Long id) {
        return expenseRepo.findById(id)
                .orElse(null);
    }

    @Override
    @Transactional
    public void deleteExpense(Long id) {
        expenseRepo.deleteById(id);
    }

    @Override
    @Transactional
    public List<Expense> saveAllExpenses(List<Expense> expenseList) {
        return expenseRepo.saveAll(expenseList);
    }


}
