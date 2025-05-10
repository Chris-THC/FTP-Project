package com.ftp.api.service;

import com.ftp.api.dto.PersonalInfoDTO;
import com.ftp.api.entity.PersonalInfo;
import com.ftp.api.exceptions.PersonalInfoException.PersonalInfoCreationException;
import com.ftp.api.exceptions.PersonalInfoException.PersonalInfoDeletionException;
import com.ftp.api.exceptions.PersonalInfoException.PersonalInfoNotFoundException;
import com.ftp.api.exceptions.PersonalInfoException.PersonalInfoUpdateException;
import com.ftp.api.form.PersonalInfoForm;
import com.ftp.api.repositori.PersonalInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:ValidationsMessages.properties")
public class PersonalInfoService {
    private final PersonalInfoRepository personalInfoRepository;

    @Value("${not.found}")
    private String notFound;

    private void validateIfInfoByUserExists(final int idUser) {
        if (!personalInfoRepository.existsById(idUser)) {
            throw new PersonalInfoNotFoundException(notFound);
        }
    }

    public List<PersonalInfoDTO> getAllPersonalInfo() {
        try {
            final List<PersonalInfo> getAll = personalInfoRepository.findAll();
            return getAll
                    .stream()
                    .map(PersonalInfoDTO::build)
                    .sorted(Comparator.comparing(PersonalInfoDTO::getIdPerInfo).reversed())
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener toda la información personal: " + e.getMessage());
        }
    }

    public PersonalInfoDTO createPersonalInfo(final PersonalInfoForm form) {
        try {
            PersonalInfo newPersonalInfo = PersonalInfo.builder()
                    .name(form.getName())
                    .lastName(form.getLastName())
                    .maternalLastName(form.getMaternalLastName())
                    .personalPath(form.getPersonalPath())
                    .build();

            personalInfoRepository.save(newPersonalInfo);
            return PersonalInfoDTO.build(newPersonalInfo);
        } catch (Exception e) {
            throw new PersonalInfoCreationException("Error al crear la información personal: " + e.getMessage());
        }
    }

    public PersonalInfoDTO getPersonalInfoById(final int idUser) {
        validateIfInfoByUserExists(idUser);
        final PersonalInfo userById = personalInfoRepository.findById(idUser)
                .orElseThrow(() -> new PersonalInfoNotFoundException(notFound));
        return PersonalInfoDTO.build(userById);
    }

    public void deletePersonalInfo(final int idUser) {
        validateIfInfoByUserExists(idUser);
        try {
            personalInfoRepository.deleteById(idUser);
        } catch (Exception e) {
            throw new PersonalInfoDeletionException("Error al eliminar la información personal con ID " + idUser + ": " + e.getMessage());
        }
    }

    public PersonalInfoDTO updatePersonalInfoFun(final PersonalInfoForm form, final int idUser) {
        validateIfInfoByUserExists(idUser);
        try {
            final PersonalInfo personalInfoToUpdate = personalInfoRepository.findById(idUser)
                    .orElseThrow(() -> new PersonalInfoNotFoundException(notFound));

            PersonalInfo userInfo = PersonalInfo.builder()
                    .name(form.getName())
                    .lastName(form.getLastName())
                    .maternalLastName(form.getMaternalLastName())
                    .personalPath(form.getPersonalPath())
                    .build();

            personalInfoToUpdate.updatePersonalInfo(userInfo);
            personalInfoRepository.save(personalInfoToUpdate);
            return PersonalInfoDTO.build(personalInfoToUpdate);
        } catch (Exception e) {
            throw new PersonalInfoUpdateException("Error al actualizar la información personal con ID " + idUser + ": " + e.getMessage());
        }
    }
}
