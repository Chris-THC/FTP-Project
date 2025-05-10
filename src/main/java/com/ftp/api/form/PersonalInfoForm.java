package com.ftp.api.form;

import jakarta.validation.constraints.Size;
import lombok.Data;
import org.jsondoc.core.annotation.ApiObjectField;

import java.io.Serializable;

@Data
public class PersonalInfoForm implements Serializable {

    @ApiObjectField(name = "name", description = "Users's name")
    @Size(max = 100, message = "{name.right.length}")
    private String name;

    @ApiObjectField(name = "lastname", description = "Users's lastname")
    @Size(max = 100, message = "{lastname.right.length}")
    private String lastName;

    @ApiObjectField(name = "maternalLastName", description = "Users's maternal lastname")
    @Size(max = 100, message = "{maternalLastName.right.length}")
    private String maternalLastName;

    @ApiObjectField(name = "personalPath", description = "Users's personal path")
    @Size(max = 300, message = "{personalPath.right.length}")
    private String personalPath;
}
