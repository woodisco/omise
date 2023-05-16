package com.kadai.omise.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/*
    会員Entity
*/
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

    @NotNull
    private int category;

    @NotEmpty
    private String zipcode;

    @NotEmpty
    private String address1;

    @NotEmpty
    private String address2;

    @NotEmpty
    private String route;

    @NotEmpty
    private String content;

    @NotEmpty
    private String filename;

    @NotEmpty
    private String filepath;
}