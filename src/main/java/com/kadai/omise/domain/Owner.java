package com.kadai.omise.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owner_id")
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

    @OneToMany(mappedBy = "owner")
    private List<Store> stores = new ArrayList<>();
}
