package com.ftp.api.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUser;

    @Column(length = 100, nullable = false)
    private String numControl;

    @Column(length = 255, nullable = false)
    private String password;

    @OneToOne
    @JoinColumn(name = "idPerInfo", referencedColumnName = "idPerInfo")
    private PersonalInfo personalInfo;

    @ManyToOne
    @JoinColumn(name = "idRol", referencedColumnName = "idRol")
    private Rol rol;
}
