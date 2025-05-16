package com.ftp.api.repositori;

import com.ftp.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtAuthRepository extends JpaRepository<User, Integer> {
    User findByNumControl(String numControl);
}
