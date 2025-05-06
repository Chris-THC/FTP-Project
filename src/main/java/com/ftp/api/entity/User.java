package com.ftp.api.entity;

import com.ftp.api.convertors.RoleConvertor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer idUser;

    @Column(name = "num_control", nullable = false, length = 100)
    private String numControl;

    @Column(name = "user_password", nullable = false, length = 255)
    private String userPassword;

    @Column(name = "user_role", nullable = false)
    @Convert(converter = RoleConvertor.class)
    private Role userRole;

    @Column(name = "id_per_info", nullable = false, length = 100)
    private Integer idPerInfo;

    public void updateUser(final User user) {
        this.numControl = Optional.ofNullable(user.getNumControl()).orElse(this.numControl);
        this.userPassword = Optional.ofNullable(user.getUserPassword()).orElse(this.userPassword);
        this.userRole = Optional.ofNullable(user.getUserRole()).orElse(this.userRole);
        this.idPerInfo = Optional.ofNullable(user.getIdPerInfo()).orElse(this.idPerInfo);
    }
}