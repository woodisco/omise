package com.kadai.omise.domain;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/*
    ログインForm
*/
@Data
public class LoginForm {
    @NotEmpty(message = "Please Write Email")
    private String email;

    @NotEmpty(message = "Please Write Password")
    private String password;
}
