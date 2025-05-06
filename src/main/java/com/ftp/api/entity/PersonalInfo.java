package com.ftp.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PersonalInfo")
public class PersonalInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPerInfo")
    private Integer idPerInfo;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "lastName", nullable = false, length = 100)
    private String lastName;

    @Column(name = "maternalLastName", length = 100)
    private String maternalLastName;

    @Column(name = "personalPath", length = 500)
    private String personalPath;
}
