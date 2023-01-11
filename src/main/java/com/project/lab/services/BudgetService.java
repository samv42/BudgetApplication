package com.project.lab.services;

import com.project.lab.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class BudgetService {
    @Autowired
    IncomeService incomeService;

    @Autowired
    ExpenseService expenseService;

    @Autowired
    DebtService debtService;

    @Autowired
    AccountService accountService;


    public void addRecurringIncome(Income income){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate now = LocalDate.now();
            Account account = accountService.getAccount(income.getAccount().getId());

            String incomeDate1 = income.getDate();
            LocalDate incomeDate2 = LocalDate.parse(incomeDate1, formatter);
            int nowDay = now.getDayOfMonth();
            int incomeDay = incomeDate2.getDayOfMonth();
            if (nowDay >= incomeDay) {
                account.setBalance(account.getBalance() + income.getAmount());
                accountService.saveAccount(account);
                incomePaymentReceived(income);
            }
    }

    public void checkIncomeListPayments(){
        List<Income> incomeList = incomeService.getAllIncomes();
        for(Income income : incomeList){
            isNextIncomeMonth(income);
            if(income.isRecurring() && income.isPaymentReceived() == false) {
                addRecurringIncome(income);
            }
        }
    }

    public void subtractRecurringExpense(Expense expense){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate now = LocalDate.now();
            //LocalDate now2 = now.format(formatter);
            double amount = expense.getAmount();
            Account account = expense.getAccount();

            String expenseDate1 = expense.getDate();
            LocalDate expenseDate2 = LocalDate.parse(expenseDate1, formatter);
            int nowDay = now.getDayOfMonth();
            int expenseDay = expenseDate2.getDayOfMonth();
            if (nowDay >= expenseDay) {
                account.setBalance(account.getBalance() - amount);
                accountService.saveAccount(account);
                expensePaymentReceived(expense);
            }
    }

    public void checkExpenseListPayments(){
        List<Expense> expensesList = expenseService.getAllExpenses();
        for(Expense expense : expensesList){
            if(expense.isRecurring()) {
                isNextExpenseMonth(expense);
                subtractRecurringExpense(expense);
            }
        }
    }

    public void subtractDebtPayment(Debt debt){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate now = LocalDate.now();
        Account account = debt.getAccount();
        double amount = debt.getPayment();

        String debtDate1 = debt.getDate();
        LocalDate debtDate2 = LocalDate.parse(debtDate1, formatter);
        int nowDay = now.getDayOfMonth();
        int debtDay = debtDate2.getDayOfMonth();
        if(nowDay >= debtDay){
            account.setBalance(account.getBalance() - amount);
            accountService.saveAccount(account);
            debtPaymentReceived(debt);
        }
    }

    public void checkDebtListPayments(){
        List<Debt> debtList = debtService.getAllDebts();
        for(Debt debt : debtList){
            isNextDebtMonth(debt);
                if(debt.isPaymentReceived() == false) {
                    subtractDebtPayment(debt);
                }
            }
        }

    public void incomePaymentReceived(Income income){
        LocalDate now = LocalDate.now();
        income.setPaymentReceived(true);
        income.setPaymentDate(now.toString());
        incomeService.saveIncome(income);
    }

    public void isNextIncomeMonth(Income income) {
        if (income.isPaymentReceived() == true) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate now = LocalDate.now();
            LocalDate debtDate = LocalDate.parse(income.getPaymentDate(), formatter);
            int month = debtDate.getMonthValue();
            int nowMonth = now.getMonthValue();

            if (nowMonth > month) {
                income.setPaymentReceived(false);
                incomeService.saveIncome(income);
            }
        }
    }

    public void expensePaymentReceived(Expense expense){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate now = LocalDate.now();
        expense.setPaymentReceived(true);
        expense.setPaymentDate(now.toString());
        expenseService.saveExpense(expense);
    }

    public void isNextExpenseMonth(Expense expense) {
        if (expense.isPaymentReceived() == true) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate now = LocalDate.now();
            LocalDate debtDate = LocalDate.parse(expense.getPaymentDate(), formatter);
            int month = debtDate.getMonthValue();
            int nowMonth = now.getMonthValue();

            if (nowMonth > month) {
                expense.setPaymentReceived(false);
                expenseService.saveExpense(expense);
            }
        }
    }

        public void debtPaymentReceived(Debt debt){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate now = LocalDate.now();
        debt.setPaymentReceived(true);
        debt.setPaymentDate(now.toString());

        //recalculates debt amount
        debt.setAmount(debt.getAmount() - debt.getPayment());
        debt.setAmount(debt.getAmount() + debt.getAmount()*debt.getInterest());
        if(debt.getAmount() == 0){
            debtService.deleteDebt(debt.getId());
        }else{
            debtService.saveDebt(debt);
        }
        }

        public void isNextDebtMonth(Debt debt) {
            if (debt.isPaymentReceived() == true) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate now = LocalDate.now();
                LocalDate debtDate = LocalDate.parse(debt.getPaymentDate(), formatter);
                int month = debtDate.getMonthValue();
                int nowMonth = now.getMonthValue();

                if (nowMonth > month) {
                    debt.setPaymentReceived(false);
                    debtService.saveDebt(debt);
                }
            }
        }

        public void transferMoney(InternalTransfer internalTransfer){
        Account transferringAccount = accountService.getAccount(internalTransfer.getTransferringAccount());
        Account targetAccount = accountService.getAccount(internalTransfer.getTargetAccount());
        transferringAccount.setBalance(transferringAccount.getBalance() - internalTransfer.getMoney());
        targetAccount.setBalance(targetAccount.getBalance() + internalTransfer.getMoney());

        accountService.saveAccount(transferringAccount);
        accountService.saveAccount(targetAccount);
        }

        public double getTotalAccountIncome(long id){
        Account account = accountService.getAccount(id);
        List<Income> incomeList = incomeService.getIncomesByAccount(account);
        double totalIncome = 0;
        for(Income income : incomeList){
            totalIncome = totalIncome + income.getAmount();
        }
        return totalIncome;
        }

        public double getTotalAccountCosts(long id){
        Account account = accountService.getAccount(id);
        List<Expense> expenseList = expenseService.getExpensesByAccount(account);
        List<Debt> debtList = debtService.getDebtsByAccount(account);
        double totalCosts = 0;
            for(Expense expense : expenseList){
                totalCosts = totalCosts + expense.getAmount();
            }
            for(Debt debt : debtList){
                totalCosts = totalCosts + debt.getPayment();
            }
            return totalCosts;
        }
        public int howLongUntilGoal(long id, double totalIncome, double totalCosts){
            Account account = accountService.getAccount(id);
            double balance = account.getBalance();
            int months = 0;
            while(balance < account.getTargetBalance()){
                balance = balance + totalIncome - totalCosts + balance*account.getInterest();
                months++;
            }
            return months;
        }
        public int howLongUntilNoMoney(long id, double totalIncome, double totalCosts){
            Account account = accountService.getAccount(id);
            double balance = account.getBalance();
            int months = 0;
            while(balance > 0){
                balance = balance + totalIncome - totalCosts + (balance*account.getInterest());
                months++;
            }
            return months;
        }
        public boolean isAccountMakingMoney(long id, double totalIncome, double totalCosts){
            Account account = accountService.getAccount(id);
            double balance = account.getBalance();
            if(totalIncome > totalCosts){
                return true;
            }else{
                if(balance + totalIncome - totalCosts + (balance*account.getInterest()) > balance){
                    return true;
                }else{
                    return false;
                }
            }
        }
    }
