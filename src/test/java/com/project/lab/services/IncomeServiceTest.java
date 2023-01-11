package com.project.lab.services;

import com.project.lab.CustomUserDetails;
import com.project.lab.models.Account;
import com.project.lab.models.Income;
import com.project.lab.repo.IncomeRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = IncomeService.class)
class IncomeServiceTest {
    @Autowired
    IncomeService incomeService;

    @MockBean
    IncomeRepo incomeRepo;

    @Test
    void getAllIncomes(){
        CustomUserDetails user = CustomUserDetails.builder()
                .id((long)1)
                .username("user")
                .build();
        Income income = Income.builder()
                .name("income")
                .amount(200)
                .recurring(true)
                .date("2022-08-08")
                .user(user)
                .build();
        List<Income> incomeList = Collections.singletonList(income);
        when(incomeRepo.findAll()).thenReturn(incomeList);
        List<Income> actual = incomeService.getAllIncomes();
        assertEquals(incomeList, actual);
    }
    @Test
    void getIncomesByAccount() {
        Account account = Account.builder()
                .name("main")
                .balance(0)
                .interest(0)
                .targetBalance(400).build();
        CustomUserDetails user = CustomUserDetails.builder()
                .id((long)1)
                .username("user")
                .build();
        Income income = Income.builder()
                .name("income")
                .amount(200)
                .recurring(true)
                .date("2022-08-08")
                .user(user)
                .build();
        List<Income> incomeList = Collections.singletonList(income);
        when(incomeService.incomeRepo.getAllIncomesByAccount(account)).thenReturn(incomeList);
        List<Income> actual = incomeService.getIncomesByAccount(account);
        assertEquals(incomeList, actual);
    }

    @Test
    void getIncome() {
        CustomUserDetails user = CustomUserDetails.builder()
                .id((long)1)
                .username("user")
                .build();
        Income income = Income.builder()
                .name("income")
                .amount(200)
                .recurring(true)
                .date("2022-08-08")
                .user(user)
                .build();
        when(incomeRepo.findById(1l)).thenReturn(java.util.Optional.ofNullable(income));
        Income actual = incomeService.getIncome(1l);
        assertEquals(income, actual);
    }

}