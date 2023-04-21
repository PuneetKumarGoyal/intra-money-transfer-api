package com.mastercard.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Transactions")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionObj {

    @Id
    @Column(name = "TRANSACTIONID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_sequence")
    @SequenceGenerator(name = "transaction_sequence", sequenceName = "TRANSACTION_SEQUENCE", allocationSize = 1)
    private int transactionId;

    @Column(name = "ACCOUNTID")
    private int accountId;

    @Column(name = "OTHERACCOUNTID")
    private int otherAccountId;

    @Column(name = "AMOUNT")
    private int amount;

    @Column(name = "CURRENCY")
    private String currency;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "TRANSACTIONDATE")
    private LocalDateTime transactionDate;

}
