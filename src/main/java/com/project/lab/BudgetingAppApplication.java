package com.project.lab;

import com.project.lab.models.Account;
import com.project.lab.services.AccountService;
import com.project.lab.models.Debt;
import com.project.lab.services.DebtService;
import org.hibernate.type.IdentifierBagType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.config.BootstrapMode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.expression.Lists;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
@EnableJpaRepositories(bootstrapMode = BootstrapMode.LAZY)
public class BudgetingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BudgetingAppApplication.class, args);
	}


	@Bean
	public CommandLineRunner loadInitialData(BudgetUserDetailsService budgetUserDetailsService, AccountService accountService, PasswordEncoder passwordEncoder) {

		return (args) -> {
			if (budgetUserDetailsService.getAllUsers().isEmpty()) {
				Role role = new Role(Role.Roles.ROLE_USER);
				Role role2 = new Role(Role.Roles.ROLE_USER);
				budgetUserDetailsService.createNewUser(
						new CustomUserDetails("user", "password",
								Collections.singletonList(role))
				);
				budgetUserDetailsService.createNewUser(
						new CustomUserDetails("user2", "password2",
								Collections.singletonList(role))
				);
			}
			if(accountService.getAllAccounts().isEmpty()){
				Account account1 = new Account("Main", "Checking", 5000,
						10000, 0.03, budgetUserDetailsService.getUser("user"));
				accountService.saveAccount(account1);
			}
		};
}

}
