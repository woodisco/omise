package com.kadai.omise.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

/*
    会員Entity
*/
@Entity
@Data
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @NotEmpty
    private String lastname;

    @NotEmpty
    private String firstname;

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;
}
