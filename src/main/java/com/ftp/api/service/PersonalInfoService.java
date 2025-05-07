package com.ftp.api.service;

import com.ftp.api.dto.PersonalInfoDTO;
import com.ftp.api.entity.PersonalInfo;
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

    private void validateIfInfoByUserExists(final int idUser) throws Exception {
        if (!personalInfoRepository.existsById(idUser)) {
            throw new Exception(notFound);
        }
    }

    public List<PersonalInfoDTO> getAllPersonalInfo() {
        final List<PersonalInfo> getAll = personalInfoRepository.findAll();
        return getAll
                .stream()
                .map(PersonalInfoDTO::build)
                .sorted(Comparator.comparing(PersonalInfoDTO::getIdPerInfo).reversed())
                .toList();
    }

    public PersonalInfoDTO createPersonalInfo(final PersonalInfoForm form) {
        PersonalInfo newPersonalInfo = PersonalInfo.builder()
                .name(form.getName())
                .lastName(form.getLastName())
                .maternalLastName(form.getMaternalLastName())
                .personalPath(form.getPersonalPath())
                .build();

        personalInfoRepository.save(newPersonalInfo);
        return PersonalInfoDTO.build(newPersonalInfo);
    }

    public PersonalInfoDTO getPersonalInfoById(final int idUser) throws Exception {
        validateIfInfoByUserExists(idUser);
        final PersonalInfo userById = personalInfoRepository.findById(idUser).get();
        return PersonalInfoDTO.build(userById);
    }

    public void deletePersonalInfo(final int idUser) throws Exception {
        validateIfInfoByUserExists(idUser);
        personalInfoRepository.deleteById(idUser);
    }

    public PersonalInfoDTO updatePersonalInfoFun(final PersonalInfoForm form, final int idUser) throws Exception {
        validateIfInfoByUserExists(idUser);
        final PersonalInfo personalInfoToUpdate = personalInfoRepository.findById(idUser).get();

        PersonalInfo userInfo = PersonalInfo.builder()
                .name(form.getName())
                .lastName(form.getLastName())
                .maternalLastName(form.getMaternalLastName())
                .personalPath(form.getPersonalPath())
                .build();

        personalInfoToUpdate.updatePersonalInfo(userInfo);
        personalInfoRepository.save(personalInfoToUpdate);
        return PersonalInfoDTO.build(personalInfoToUpdate);
    }


}
