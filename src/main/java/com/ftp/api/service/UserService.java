package com.ftp.api.service;

import com.ftp.api.dto.PersonalInfoDTO;
import com.ftp.api.dto.UserDTO;
import com.ftp.api.entity.User;
import com.ftp.api.form.UserForm;
import com.ftp.api.repositori.PersonalInfoRepository;
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
    private final PersonalInfoRepository personalInfoRepository;

    @Value("${not.found}")
    private String notFound;

    private void validateIfUserExists(final int idUser) throws Exception {
        if (!userRepository.existsById(idUser)) {
            throw new Exception(notFound);
        }
    }

    public UserDTO createUser(final UserForm form) {
        User user = User.builder()
                .numControl(form.getControlNumber())
                .userPassword(form.getPassword())
                //.password(passwordEncoder.encode(form.getPassword())) // Descomentar hasta gregar la encriptacion de la contraseña
                .userRole(form.getUserRole())
                .idPersonalInfo(form.getIdPersonalInfo())
                .build();

        userRepository.save(user);
        return UserDTO.build(user);
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

//    Inner join
public List<UserDTO> getUsersWithPersonalInfo() {
    return userRepository.findAll().stream().map(user -> {
        // Obtén la lista de PersonalInfoDTO
        List<PersonalInfoDTO> personalInfoList = personalInfoRepository.findById(user.getIdPersonalInfo())
                .stream()
                .map(PersonalInfoDTO::build)
                .toList();

        // Construye el UserDTO con la lista de PersonalInfoDTO
        return UserDTO.builder()
                .idUser(user.getIdUser())
                .numControl(user.getNumControl())
                .userRole(user.getUserRole())
                .idPersonalInfo(user.getIdPersonalInfo())
                .personalInfo(personalInfoList) // Aquí se pasa la lista correcta
                .build();
    }).toList();
}
}
