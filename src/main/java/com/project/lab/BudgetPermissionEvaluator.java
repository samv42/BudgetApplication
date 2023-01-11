package com.project.lab;

import com.project.lab.models.Account;
import com.project.lab.models.Debt;
import com.project.lab.models.Expense;
import com.project.lab.models.Income;
import com.project.lab.repo.AccountRepo;
import com.project.lab.repo.DebtRepo;
import com.project.lab.repo.ExpenseRepo;
import com.project.lab.repo.IncomeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

@Component
public class BudgetPermissionEvaluator implements PermissionEvaluator {
    @Autowired
    IncomeRepo incomeRepo;

    @Autowired
    ExpenseRepo expenseRepo;

    @Autowired
    DebtRepo debtRepo;

    @Autowired
    AccountRepo accountRepo;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return false;
    }


    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        if (!permission.getClass().equals("".getClass())) {
            throw new SecurityException("Cannot execute hasPermission() calls where permission is not in String form");
        }

        if (userIsAdmin(authentication)) {
            return true;
        } else {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            if (targetType.equalsIgnoreCase("income")) {
                Income income = incomeRepo.findById(Long.parseLong(targetId.toString()))
                        .orElseThrow(()-> new EntityNotFoundException("The income you are trying to access does not exist"));

                return income.getUser().equals(userDetails.getUsername());

            } else if (targetType.equalsIgnoreCase("expense")) {
                Expense expense = expenseRepo.findById(Long.parseLong(targetId.toString()))
                        .orElseThrow(()-> new EntityNotFoundException("The income you are trying to access does not exist"));

                return expense.getUser().equals(userDetails.getUsername());
            } else if (targetType.equalsIgnoreCase("debt")) {
                Debt debt = debtRepo.findById(Long.parseLong(targetId.toString()))
                        .orElseThrow(()-> new EntityNotFoundException("The income you are trying to access does not exist"));


                return debt.getUser().equals(userDetails.getUsername());
            } else if (targetType.equalsIgnoreCase("account")) {
                Account account = accountRepo.findById(Long.parseLong(targetId.toString()))
                        .orElseThrow(()-> new EntityNotFoundException("The income you are trying to access does not exist"));

                return account.getUser().equals(userDetails.getUsername());
            }
            return true;
        }
    }
    public boolean userIsAdmin (Authentication authentication){
        Collection<Role> grantedAuthorities = (Collection<Role>) authentication.getAuthorities();

        for (Role r : grantedAuthorities) {
            if (r.getAuthority().equals("ROLE_ADMIN")) {
                return true;
            }
        }
        return false;
    }

}
