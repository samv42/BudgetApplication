package com.project.lab.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class InternalTransfer {
    double money;
    long transferringAccount;
    long targetAccount;
}
