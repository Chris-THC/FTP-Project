package com.ftp.api.form;

import com.ftp.api.entity.Role;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.jsondoc.core.annotation.ApiObjectField;

import java.io.Serializable;

@Data
public class UserForm implements Serializable {

    @ApiObjectField(name = "controlNumber", description = "Users's control number")
    @Size(max = 20, message = "{controlNumber.right.length}")
    private String controlNumber;

    @ApiObjectField(name = "password", description = "User's password")
    @Size(max = 100, message = "{name.right.length}")
    private String password;

    @ApiObjectField(name = "role", description = "User's role")
    private Role userRole;

    @ApiObjectField(name = "idPersonalInfo", description = "User's id personal info")
    private Integer idPersonalInfo;
}
