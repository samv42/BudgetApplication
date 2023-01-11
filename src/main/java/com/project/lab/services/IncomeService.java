package com.project.lab.services;

import com.project.lab.CustomUserDetails;
import com.project.lab.models.Account;
import com.project.lab.models.Income;
import com.project.lab.repo.IncomeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class IncomeService implements IncomeServiceInterface {
    @Autowired
    IncomeRepo incomeRepo;

    @Override
    public List<Income> getAllIncomes() {
        return incomeRepo.findAll();
    }

    @Override
    public List <Income> getIncomesByAccount(Account account) {return incomeRepo.getAllIncomesByAccount(account);}

    @Override
    @Transactional
    public Income saveIncome(Income income) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        income.setUser(user);
        return incomeRepo.save(income);
    }

    @Override
    public Income getIncome(Long id) {
        return incomeRepo.findById(id)
                .orElse(null);
    }

    @Override
    @Transactional
    public void deleteIncome(Long id) {
        incomeRepo.deleteById(id);
    }

    @Override
    @Transactional
    public List<Income> saveAllIncomes(List<Income> incomeList) {
        return incomeRepo.saveAll(incomeList);
    }

}
