package com.ftp.api.repositori;

import com.ftp.api.entity.User;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

@Registered
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByIdPersonalInfo(Integer idPersonalInfo);
    Optional<User> findByNumControl(String numControl);
}