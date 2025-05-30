package com.ftp.api.form;

import lombok.Data;

@Data
public class UserRegistrationForm {
    private PersonalInfoForm personalInfoForm;
    private UserForm userForm;
}