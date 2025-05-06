package com.ftp.api.dto;

import com.ftp.api.entity.Role;
import com.ftp.api.entity.User;
import lombok.Builder;
import lombok.Getter;
import org.jsondoc.core.annotation.ApiObjectField;

@Builder
@Getter
public class UserDTO {
    @ApiObjectField(name = "id", description = "User ID")
    private int idUser;

    @ApiObjectField(name = "numControl", description = "User's number control")
    private String numControl;

    @ApiObjectField(name = "userRole", description = "User's role")
    private Role userRole;

    @ApiObjectField(name = "idPersonalInfo", description = "User's personal info ID")
    private Integer idPersonalInfo;

    public static UserDTO build(final User user) {
        return UserDTO.builder()
                .idUser(user.getIdUser())
                .numControl(user.getNumControl())
                .userRole(user.getUserRole())
                .idPersonalInfo(user.getIdPerInfo())
                .build();
    }
}
