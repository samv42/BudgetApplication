package com.project.lab.services;

import com.project.lab.models.Account;
import com.project.lab.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AccountService implements AccountServiceInterface {
    @Autowired
    AccountRepo accountRepo;

    @Override
    public List<Account> getAllAccounts() {
        return accountRepo.findAll();
    }

    @Override
    @Transactional
    public Account saveAccount(Account account) {
        return accountRepo.save(account);
    }

    @Override
    public Account getAccount(Long id) {
        return accountRepo.findById(id)
                .orElse(null);
    }

    @Override
    @Transactional
    public void deleteAccount(Long id) {
        accountRepo.deleteById(id);
    }

    @Override
    @Transactional
    public List<Account> saveAllAccounts(List<Account> accountList) {
        return accountRepo.saveAll(accountList);
    }
    /*public Account addNewAccount(){
        Account account = new Account();
        return account;
    }

    public ArrayList getAccountList(){
        return accountRepo.accountList;
    }

    public int isIdCorrect(int id){
        int idCorrect = id;
        int i = 0;
        for(Account iterator : accountRepo.accountList){
            if(iterator.getId() == id && accountRepo.accountList.indexOf(iterator) != id){
                idCorrect = accountRepo.accountList.indexOf(iterator);
            }
            i++;
        }
        return idCorrect;
    }

    public Account editAccount(int id){
        Account account = new Account();
        for(Account iterator : accountRepo.accountList){
            if(iterator.getId() == id){
                account = iterator;
            }
        }
        return account;
    }

    public void saveAccount(Account account){
        accountRepo.accountList.add(account);
    }

    public void updateAccount(int id, Account account){
        accountRepo.accountList.set(isIdCorrect(id), account);
    }
    public void deleteAccount(int id){
        int idCorrect = id;
        int i = 0;
        for(Account iterator : accountRepo.accountList){
            if(iterator.getId() == id && accountRepo.accountList.indexOf(iterator) != id){
                idCorrect = accountRepo.accountList.indexOf(iterator);
            }
            i++;
        }
        accountRepo.accountList.remove(idCorrect);
    }*/
}
