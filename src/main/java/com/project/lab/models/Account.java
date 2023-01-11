package com.project.lab.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.lab.CustomUserDetails;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "accounts")
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String type;
    private double balance;
    private double targetBalance;
    private double interest;

    @ManyToOne(optional = false)
    @JoinColumn
    @JsonIgnore
    private CustomUserDetails user;

    public Account(String name, String type, double balance, double targetBalance, double interest, CustomUserDetails user){
        this.name = name;
        this.type = type;
        this.balance = balance;
        this.targetBalance = targetBalance;
        this.interest = interest;
        this.user = user;
    }
    public String getUser() {
        return user.getUsername();
    }
}
