package com.kadai.omise.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owner_id")
    private Long id;

    @NotEmpty(message = "Please Write Last Name")
    @Setter
    private String lastname;

    @NotEmpty(message = "Please Write First Name")
    @Setter
    private String firstname;

    @NotEmpty(message = "Please Write Email")
    @Email(message = "正しい形式で入力してください")
    @Setter
    private String email;

    @NotEmpty(message = "Please Write Password")
    @Setter
    private String password;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Store> stores = new ArrayList<>();

    public void putStore(Store store) {

        this.stores.add(store);
    }
}