package com.project.lab.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.lab.CustomUserDetails;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "expenses")
@Data

public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String type;
    private String date;
    private boolean recurring;
    private double amount;

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
