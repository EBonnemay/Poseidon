package com.nnk.springboot.domain;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.sql.Timestamp;


@Entity
@Table(name = "trade")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor

/**
 * This entity class defines Trade objects. It has two constructors : one without parameter,
 * one with parameters 'account', 'type' , 'buy_quantity'
 *
 * @author Emmanuelle Bonnemay
 * created on 23/04/2023
 */
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="trade_id")
    Integer trade_id;

    @Column(name= "account")
    @NonNull
    @NotBlank(message = "account is mandatory")
    String account;
    @Column(name= "type")
    @NonNull
    @NotBlank(message = "type is mandatory")
    String type;
    @Column(name= "buy_quantity")
    @NonNull
    @NotNull(message="buy_quantity is mandatory")
    @PositiveOrZero(message = "buy_quantity must be positive or zero")
    @Digits(integer = 9, fraction = 2, message = "no more than 9 digits before decimal point, no more than 2 digits after decimal point")
    Double buy_quantity;
    @Column
    Double sellQuantity;
    @Column
    Double buyPrice;
    @Column
    Double sellPrice;
    @Column
    String benchmark;
    @Column
    Timestamp tradeDate;
    @Column
    String security;
    @Column
    String status;
    @Column
    String trader;
    @Column
    String book;
    @Column
    String creationName;
    @Column
    Timestamp creationDate;
    @Column
    String revisionName;
    @Column
    Timestamp revisionDate;
    @Column
    String dealName;
    @Column
    String dealType;
    @Column
    String sourceListId;
    @Column
    String side;

}
