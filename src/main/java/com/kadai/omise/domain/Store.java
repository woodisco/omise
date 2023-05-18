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

    @NotEmpty(message = "Please Write Name")
    private String name;

    @NotEmpty(message = "Please Select Category")
    private String category;

    @NotEmpty(message = "Please Write Zipcode")
    private String zipcode;

    @NotEmpty(message = "Please Write State/Province")
    private String address1;

    @NotEmpty(message = "Please Write City")
    private String address2;

    @NotEmpty(message = "Please Write Route")
    private String route;

    @NotEmpty(message = "Please Write Content")
    private String content;

    @NotEmpty(message = "Please Upload Filename")
    private String filename;

    @NotEmpty
    private String filepath;
}