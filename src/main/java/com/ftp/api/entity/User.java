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
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUser")
    private Integer idUser;

    @Column(name = "numControl", nullable = false, length = 100)
    private String numControl;

    @Column(name = "userPassword", nullable = false, length = 255)
    private String userPassword;

    @Column(name = "userRole", nullable = false)
    @Convert(converter = RoleConvertor.class)
    private Role userRole;
}