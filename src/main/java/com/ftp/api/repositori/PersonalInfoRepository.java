package com.ftp.api.repositori;

import com.ftp.api.entity.PersonalInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonalInfoRepository extends JpaRepository<PersonalInfo, Integer> {
    Optional<PersonalInfo> findByNameAndLastNameAndMaternalLastName(String name, String lastName, String maternalLastName);
}
