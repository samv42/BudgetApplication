package com.project.lab;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity(debug = true)
public class SecurConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
               /* .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/index"))
                .logout(logout -> logout
                        .logout()
                        .logoutSuccessUrl("/login"))*/
                .csrf(csrf -> csrf.disable())
                .formLogin().and()
                .logout().and()
                .authorizeRequests(auth -> auth
                        .antMatchers("/css", "/js", "/error", "/index").permitAll()
                        /*.antMatchers(HttpMethod.GET, "/income", "/expenses", "/debt", "/accounts").permitAll()
                        .antMatchers(HttpMethod.POST, "/income", "/expenses", "/debt", "/accounts").permitAll()
                        .antMatchers(HttpMethod.DELETE, "/income", "/expenses", "/debt", "/accounts").permitAll()
                        .antMatchers(HttpMethod.PATCH, "/income", "/expenses", "/debt", "/accounts").permitAll()*/
                        .anyRequest().authenticated())

                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

   // @Bean
    //public PasswordEncoder passwordEncoder() {
   //     return new CustomPasswordEncoder();
  //  }

    /*class CustomPasswordEncoder implements PasswordEncoder{

        @Override
        public String encode(CharSequence rawPassword) {
            return rawPassword.toString();
        }

        @Override
        public boolean matches(CharSequence rawPassword, String encodedPassword) {
            return rawPassword.toString().equals(encodedPassword);
        }
    }*/

