package com.ftp.api.dto;

import lombok.Builder;
import lombok.Getter;
import org.jsondoc.core.annotation.ApiObjectField;

@Builder
@Getter
public class PersonalInfoDTO {
    @ApiObjectField(name = "idPerInfo", description = "User's idPerInfo")
    private Integer idPerInfo;

    @ApiObjectField(name = "name", description = "User's name")
    private String name;

    @ApiObjectField(name = "lastName", description = "User's last name")
    private String lastName;

    @ApiObjectField(name = "maternalLastName", description = "User's maternal last name")
    private String maternalLastName;

    @ApiObjectField(name = "personalPath", description = "User's personal path")
    private String personalPath;
}
