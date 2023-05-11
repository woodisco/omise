package com.kadai.omise.domain;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/* ログインForm */
@Data
public class LoginForm {
    @NotEmpty
    private String Email;

    @NotEmpty
    private String password;
}
