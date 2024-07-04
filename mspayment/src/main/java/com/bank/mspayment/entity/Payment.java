package com.bank.mspayment.entity;

import com.bank.mspayment.config.json.CustomLocalDateDeserializer;
import com.bank.mspayment.config.json.CustomLocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "ID")
    private UUID id;

    @Column(name = "CUSTOMER_ID", nullable = false)
    private Long customerId;

    @Column(name = "CATEGORY_ID", nullable = false)
    private Long categoryId;

    @Column(name = "TOTAL", nullable = false)
    private Double total;

    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDateTime createdDate;
}
