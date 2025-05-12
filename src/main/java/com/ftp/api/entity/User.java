package com.ftp.api.entity;

import com.ftp.api.convertors.RoleConvertor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User implements UserDetails {
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
    private Integer idPersonalInfo;

    public void updateUser(final User user) {
        this.numControl = Optional.ofNullable(user.getNumControl()).orElse(this.numControl);
        this.userPassword = Optional.ofNullable(user.getUserPassword()).orElse(this.userPassword);
        this.userRole = Optional.ofNullable(user.getUserRole()).orElse(this.userRole);
        this.idPersonalInfo = Optional.ofNullable(user.getIdPersonalInfo()).orElse(this.idPersonalInfo);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userRole.name()));
    }

    @Override
    public String getPassword() {
        return this.userPassword;
    }

    @Override
    public String getUsername() {
        return this.numControl;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}