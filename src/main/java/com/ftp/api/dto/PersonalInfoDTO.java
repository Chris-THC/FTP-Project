package com.ftp.api.dto;

import com.ftp.api.entity.PersonalInfo;
import lombok.Builder;
import lombok.Getter;
import org.jsondoc.core.annotation.ApiObjectField;

@Builder
@Getter
public class PersonalInfoDTO {
    @ApiObjectField(name = "idPersonalInfo", description = "User's idPerInfo")
    private Integer idPerInfo;

    @ApiObjectField(name = "name", description = "User's name")
    private String name;

    @ApiObjectField(name = "lastName", description = "User's last name")
    private String lastName;

    @ApiObjectField(name = "maternalLastName", description = "User's maternal last name")
    private String maternalLastName;

    @ApiObjectField(name = "personalPath", description = "User's personal path")
    private String personalPath;

    public static PersonalInfoDTO build(final PersonalInfo userInfo) {
        return PersonalInfoDTO.builder()
                .idPerInfo(userInfo.getIdPerInfo())
                .name(userInfo.getName())
                .lastName(userInfo.getLastName())
                .maternalLastName(userInfo.getMaternalLastName())
                .personalPath(userInfo.getPersonalPath())
                .build();
    }
}
