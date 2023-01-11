package com.project.lab.services;

import com.project.lab.models.Account;

import java.util.List;

public interface AccountServiceInterface {
    List<Account> getAllAccounts();

    Account saveAccount(Account account);

    Account getAccount(Long id);

    void deleteAccount(Long id);

    List<Account> saveAllAccounts(List<Account> accountList);
}
