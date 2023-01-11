package com.project.lab.repo;

import com.project.lab.models.Account;
import com.project.lab.models.Expense;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@Getter
//@Setter
//@Component
@Repository
public interface ExpenseRepo extends JpaRepository<Expense, Long> {
    List<Expense> getAllExpensesByAccount(Account account);
}
