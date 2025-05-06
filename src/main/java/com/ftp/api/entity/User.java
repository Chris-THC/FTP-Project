package com.ftp.api.entity;

import com.ftp.api.convertors.RoleConvertor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
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
}