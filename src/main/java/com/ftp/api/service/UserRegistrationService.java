package com.ftp.api.service;

import com.ftp.api.dto.PersonalInfoDTO;
import com.ftp.api.dto.UserDTO;
import com.ftp.api.form.PersonalInfoForm;
import com.ftp.api.form.UserForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {
    private final FtpService ftpService;
    private final PersonalInfoService personalInfoService;
    private final UserService userService;

    public UserDTO registerUserWithFtpDirectory(PersonalInfoForm personalInfoForm, UserForm userForm) throws IOException {
        // Crear el directorio en el servidor FTP
        String ftpPath = String.format("/home/admin/ftp-data/%s-%s-%s",
                userForm.getControlNumber(),
                personalInfoForm.getName(),
                personalInfoForm.getLastName());

        System.out.println(ftpPath);

        boolean directoryCreated = ftpService.createDirectory(ftpPath);

        if (!directoryCreated) {
            System.out.println("Error al crear el directorio en el servidor FTP.");
            throw new IOException("No se pudo crear el directorio en el servidor FTP.");
        }

        // Crear la información personal
        PersonalInfoDTO personalInfoDTO = personalInfoService.createPersonalInfo(personalInfoForm);

        // Crear el usuario con el ID de la información personal
        userForm.setIdPersonalInfo(personalInfoDTO.getIdPerInfo());
        return userService.createUser(userForm);
    }
}