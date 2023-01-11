package com.project.lab.repo;

import com.project.lab.models.Account;
import com.project.lab.models.Debt;
import com.project.lab.models.Expense;
import com.project.lab.models.Income;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public interface DebtRepo extends JpaRepository<Debt, Long> {
    List<Debt> getAllDebtsByAccount(Account account);

}
