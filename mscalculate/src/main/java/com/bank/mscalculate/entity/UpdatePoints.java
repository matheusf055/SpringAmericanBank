package com.bank.mscalculate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class UpdatePoints {

    private Long customerId;
    private int points;
}
