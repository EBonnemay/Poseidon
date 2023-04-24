package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.sql.Timestamp;


@Entity
@Table(name = "curvepoint")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class CurvePoint {
    /**
     * This entity class defines Curvepoint objects. It has two constructors : one without parameter,
     * one with parameters 'curve_point_id', 'term' , 'value'.
     *
     * @author Emmanuelle Bonnemay
     * created on 23/04/2023
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="curve_id")
    Integer curve_id;

    @Column (name = "curve_point_id")
    @NonNull
    @NotNull(message = "curve_point_id is mandatory")
    @PositiveOrZero(message = "curve_point_id must be positive or zero")
    private Integer curve_point_id;
    @Column(name = "as_of_date")
    private Timestamp as_of_date;
    @Column(name="term")
    @NonNull
    @NotNull(message = "term is mandatory")
    @PositiveOrZero(message = "term must be positive or zero")
    @Digits(integer = 9, fraction = 2, message = "no more than 9 digits before decimal point, no more than 2 digits after decimal point")
    private Double term;

    @Column(name = "value")
    @NonNull
    @NotNull(message = "value is mandatory")
    @PositiveOrZero(message = "value be positive or zero")
    @Digits(integer = 9, fraction = 2, message = "no more than 9 digits before decimal point, no more than 2 digits after decimal point")
    private  Double value;

    @Column(name = "creation_date")
    private Timestamp creation_date;
}
