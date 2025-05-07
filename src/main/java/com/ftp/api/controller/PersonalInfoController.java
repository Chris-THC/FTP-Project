package com.ftp.api.controller;

import com.ftp.api.dto.PersonalInfoDTO;
import com.ftp.api.form.PersonalInfoForm;
import com.ftp.api.service.PersonalInfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/personal/information")
@RequiredArgsConstructor
public class PersonalInfoController {
    private final PersonalInfoService personalInfoService;

    @GetMapping(path = "")
    public ResponseEntity<List<PersonalInfoDTO>> getAllUsers() {
        final List<PersonalInfoDTO> personalInformationList = personalInfoService.getAllPersonalInfo();
        return ResponseEntity.ok().body(personalInformationList);
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<PersonalInfoDTO> getPersonalInfoById(@PathVariable("userId") final int userId) throws Exception {
        final PersonalInfoDTO userDTOInfo = personalInfoService.getPersonalInfoById(userId);
        return ResponseEntity.ok().body(userDTOInfo);
    }

    @PostMapping(path = "")
    public ResponseEntity<PersonalInfoDTO> savePersonalInfoById(@RequestBody @Valid final PersonalInfoForm form) {
        final PersonalInfoDTO saveUserInfo = personalInfoService.createPersonalInfo(form);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveUserInfo);
    }

    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<Void> deletePersonalInfo(@PathVariable("userId") final int userId) throws Exception {
        personalInfoService.deletePersonalInfo(userId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "/{userId}")
    public ResponseEntity<PersonalInfoDTO> updatePersonalInfo(@RequestBody @Valid final PersonalInfoForm form, @PathVariable("userId") final int userId) throws Exception {
        final PersonalInfoDTO updateUserInfo = personalInfoService.updatePersonalInfoFun(form, userId);
        return ResponseEntity.ok().body(updateUserInfo);
    }
}
