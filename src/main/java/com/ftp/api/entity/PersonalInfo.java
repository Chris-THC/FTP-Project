package com.ftp.api.entity;

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
@Table(name = "personal_info")
public class PersonalInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_per_info")
    private Integer idPerInfo;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "maternal_last_name", length = 100)
    private String maternalLastName;

    @Column(name = "personal_path", length = 500)
    private String personalPath;

    public void updatePersonalInfo(final PersonalInfo personalInfoForm) {
        this.name = Optional.ofNullable(personalInfoForm.getName()).orElse(this.name);
        this.lastName = Optional.ofNullable(personalInfoForm.getLastName()).orElse(this.lastName);
        this.maternalLastName = Optional.ofNullable(personalInfoForm.getMaternalLastName()).orElse(this.maternalLastName);
        this.personalPath = Optional.ofNullable(personalInfoForm.getPersonalPath()).orElse(this.personalPath);
    }
}