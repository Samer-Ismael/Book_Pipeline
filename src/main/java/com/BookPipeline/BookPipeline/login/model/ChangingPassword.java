package com.BookPipeline.BookPipeline.login.model;

import lombok.Data;

@Data
public class ChangingPassword {
    String oldPassword;
    String newPassword;
    String confirmPassword;
}
