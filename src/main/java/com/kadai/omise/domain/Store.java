package com.kadai.omise.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/* 会員Entity */
@Entity
@Getter
@Setter
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private int category;

    @NotEmpty
    private int address1;

    @NotEmpty
    private int address2;

    @NotEmpty
    private int address3;

    @NotEmpty
    private int station;
}
