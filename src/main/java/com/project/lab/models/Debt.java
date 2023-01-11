package com.project.lab.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.lab.CustomUserDetails;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "debts")
@Data
public class Debt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String creditor;
    private String type;
    private String date;
    private double amount;
    private double payment;
    private double interest;

    private boolean paymentReceived = false;
    private String paymentDate;

    @ManyToOne (optional = false)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(optional = false)
    @JoinColumn
    @JsonIgnore
    private CustomUserDetails user;

    public String getUser() {
        return user.getUsername();
    }
}
