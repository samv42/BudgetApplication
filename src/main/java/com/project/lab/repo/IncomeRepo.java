package com.project.lab.repo;

import com.project.lab.models.Account;
import com.project.lab.models.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface IncomeRepo extends JpaRepository<Income, Long> {
    List<Income> getAllIncomesByAccount(Account account);
    //List<Income> getAllIncomesByType(Type type);
}
