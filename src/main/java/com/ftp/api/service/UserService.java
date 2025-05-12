package com.ftp.api.service;

import com.ftp.api.dto.PersonalInfoDTO;
import com.ftp.api.dto.UserDTO;
import com.ftp.api.entity.PersonalInfo;
import com.ftp.api.entity.User;
import com.ftp.api.exceptions.UserException.UserCreationException;
import com.ftp.api.exceptions.UserException.UserDeletionException;
import com.ftp.api.exceptions.UserException.UserNotFoundException;
import com.ftp.api.exceptions.UserException.UserUpdateException;
import com.ftp.api.form.UserForm;
import com.ftp.api.repositori.PersonalInfoRepository;
import com.ftp.api.repositori.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:ValidationsMessages.properties")
public class UserService {
    private final UserRepository userRepository;
    private final PersonalInfoRepository personalInfoRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${not.found}")
    private String notFound;

    private void validateIfUserExists(final int idUser) {
        if (!userRepository.existsById(idUser)) {
            throw new UserNotFoundException(notFound);
        }
    }

    public UserDTO createUser(final UserForm form) {
        try {
            User user = User.builder()
                    .numControl(form.getControlNumber())
                    .userPassword(passwordEncoder.encode(form.getPassword()))
                    .userRole(form.getUserRole())
                    .idPersonalInfo(form.getIdPersonalInfo())
                    .build();

            userRepository.save(user);
            return UserDTO.build(user);
        } catch (Exception e) {
            throw new UserCreationException("Error al crear el usuario: " + e.getMessage());
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

    public UserDTO getUserById(final int idUser) {
        validateIfUserExists(idUser);
        final User user = userRepository.findById(idUser).orElseThrow(() -> new UserNotFoundException(notFound));
        return UserDTO.build(user);
    }

    public void deleteUser(final int idUser) {
        validateIfUserExists(idUser);
        try {
            userRepository.deleteById(idUser);
        } catch (Exception e) {
            throw new UserDeletionException("Error al eliminar el usuario con ID " + idUser + ": " + e.getMessage());
        }
    }

    public UserDTO updateUser(final UserForm form, final int idUser) {
        validateIfUserExists(idUser);
        try {
            final User userUpdate = userRepository.findById(idUser).orElseThrow(() -> new UserNotFoundException(notFound));

            User userInfo = User.builder()
                    .numControl(form.getControlNumber())
                    .userPassword(passwordEncoder.encode(form.getPassword()))
                    .userRole(form.getUserRole())
                    .build();

            userUpdate.updateUser(userInfo);
            userRepository.save(userUpdate);
            return UserDTO.build(userUpdate);
        } catch (Exception e) {
            throw new UserUpdateException("Error al actualizar el usuario con ID " + idUser + ": " + e.getMessage());
        }
    }

    public List<UserDTO> getUsersWithPersonalInfo() {
        return userRepository.findAll().stream().map(user -> {
            List<PersonalInfoDTO> personalInfoList = personalInfoRepository.findById(user.getIdPersonalInfo())
                    .stream()
                    .map(PersonalInfoDTO::build)
                    .toList();

            return UserDTO.builder()
                    .idUser(user.getIdUser())
                    .numControl(user.getNumControl())
                    .userRole(user.getUserRole())
                    .idPersonalInfo(user.getIdPersonalInfo())
                    .personalInfo(personalInfoList)
                    .build();
        }).toList();
    }

    public UserDTO getUserWithPersonalInfoById(int userId) {
        validateIfUserExists(userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(notFound));
        List<PersonalInfoDTO> personalInfoList = personalInfoRepository.findById(user.getIdPersonalInfo())
                .stream()
                .map(PersonalInfoDTO::build)
                .toList();

        return UserDTO.builder()
                .idUser(user.getIdUser())
                .numControl(user.getNumControl())
                .userRole(user.getUserRole())
                .idPersonalInfo(user.getIdPersonalInfo())
                .personalInfo(personalInfoList)
                .build();
    }

    public UserDTO getUserWithPersonalInfoByFullName(String name, String lastName, String maternalLastName) {
        PersonalInfo personalInfo = personalInfoRepository.findByNameAndLastNameAndMaternalLastName(name, lastName, maternalLastName)
                .orElseThrow(() -> new UserNotFoundException(notFound));

        User user = userRepository.findByIdPersonalInfo(personalInfo.getIdPerInfo())
                .orElseThrow(() -> new UserNotFoundException(notFound));

        List<PersonalInfoDTO> personalInfoList = List.of(PersonalInfoDTO.build(personalInfo));

        return UserDTO.builder()
                .idUser(user.getIdUser())
                .numControl(user.getNumControl())
                .userRole(user.getUserRole())
                .idPersonalInfo(user.getIdPersonalInfo())
                .personalInfo(personalInfoList)
                .build();
    }

    public UserDTO getUserByNumControl(String numControl) {
        User user = userRepository.findByNumControl(numControl)
                .orElseThrow(() -> new UserNotFoundException(notFound));

        List<PersonalInfoDTO> personalInfoList = personalInfoRepository.findById(user.getIdPersonalInfo())
                .stream()
                .map(PersonalInfoDTO::build)
                .toList();

        return UserDTO.builder()
                .idUser(user.getIdUser())
                .numControl(user.getNumControl())
                .userRole(user.getUserRole())
                .idPersonalInfo(user.getIdPersonalInfo())
                .personalInfo(personalInfoList)
                .build();
    }
}