package com.kadai.omise.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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

    @NotEmpty(message = "Please Write Last Name")
    private String lastname;

    @NotEmpty(message = "Please Write First Name")
    private String firstname;

    @NotEmpty(message = "Please Write Email")
    @Email(message = "正しい形式で入力してください")
    private String email;

    @NotEmpty(message = "Please Write Password")
    private String password;
}
