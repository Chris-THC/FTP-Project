package com.ftp.api.service;

import com.ftp.api.dto.UserDTO;
import com.ftp.api.entity.User;
import com.ftp.api.form.UserForm;
import com.ftp.api.repositori.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:ValidationsMessages.properties")
public class UserService {
    private final UserRepository userRepository;

    @Value("${not.found}")
    private String notFound;

    public UserDTO createUser(final UserForm form) {
        User user = User.builder()
                .numControl(form.getControlNumber())
                .userPassword(form.getPassword())
                //.password(passwordEncoder.encode(form.getPassword())) // Descomentar hasta gregar la encriptacion de la contraseña
                .userRole(form.getUserRole())
                .build();

        userRepository.save(user);
        return UserDTO.build(user);
    }

    private void validateIfUserExists(final int idUser) throws Exception {
        if (!userRepository.existsById(idUser)) {
            throw new Exception(notFound);
        }
    }

    public List<UserDTO> getAllUsers() {
        final List<User> getAll = userRepository.findAll();
        return getAll
                .stream()
                .map(UserDTO::build)
                .sorted(Comparator.comparing(UserDTO::getIdUser).reversed())
                .toList();
    }

    public UserDTO getUserById(final int idUser) throws Exception {
        validateIfUserExists(idUser);
        final User user = userRepository.findById(idUser).get();
        return UserDTO.build(user);
    }

    public void deleteUser(final int idUser) throws Exception {
        validateIfUserExists(idUser);
        userRepository.deleteById(idUser);
    }

    public UserDTO updateUser(final UserForm form, final int idUser) throws Exception {
        validateIfUserExists(idUser);
        final User userUpdate = userRepository.findById(idUser).get();

        User userInfo = User.builder()
                .numControl(form.getControlNumber())
                .userPassword(form.getPassword())
                //.password(passwordEncoder.encode(form.getPassword())) // Descomentar hasta gregar la encriptacion de la contraseña
                .userRole(form.getUserRole())
                .build();

        userUpdate.updateUser(userInfo);
        userRepository.save(userUpdate);
        return UserDTO.build(userUpdate);
    }
}
