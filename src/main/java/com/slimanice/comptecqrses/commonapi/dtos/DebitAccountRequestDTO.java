package com.slimanice.comptecqrses.commonapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DebitAccountRequestDTO {
    private String accountId;
    private double amount;
    private String currency;
}
