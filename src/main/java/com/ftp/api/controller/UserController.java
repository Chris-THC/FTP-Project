package com.ftp.api.controller;

import com.ftp.api.dto.UserDTO;
import com.ftp.api.form.PersonalInfoForm;
import com.ftp.api.form.UserForm;
import com.ftp.api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(path = "")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        final List<UserDTO> userDTOList = userService.getAllUsers();
        return ResponseEntity.ok().body(userDTOList);
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") final int userId) throws Exception {
        final UserDTO userDTOInfo = userService.getUserById(userId);
        return ResponseEntity.ok().body(userDTOInfo);
    }

    @PostMapping(path = "")
    public ResponseEntity<UserDTO> saveUser(@RequestBody @Valid final UserForm form) {
        final UserDTO saveUserInfo = userService.createUser(form);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveUserInfo);
    }

    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") final int userId) throws Exception {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "/{userId}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody @Valid final UserForm form, @PathVariable("userId") final int userId) throws Exception {
        final UserDTO updateUserInfo = userService.updateUser(form, userId);
        return ResponseEntity.ok().body(updateUserInfo);
    }

    @GetMapping(path = "/information")
    public ResponseEntity<List<UserDTO>> getUsersWithPersonalInfo() {
        List<UserDTO> usersWithPersonalInfo = userService.getUsersWithPersonalInfo();
        return ResponseEntity.ok().body(usersWithPersonalInfo);
    }

    @GetMapping(path = "/information/{userId}")
    public ResponseEntity<UserDTO> getUserWithPersonalInfoById(@PathVariable("userId") int userId) throws Exception {
        UserDTO userWithPersonalInfo = userService.getUserWithPersonalInfoById(userId);
        return ResponseEntity.ok().body(userWithPersonalInfo);
    }

    @PostMapping(path = "/information/fullname")
    public ResponseEntity<UserDTO> getUserWithPersonalInfoByFullName(@RequestBody PersonalInfoForm personalInfoForm) throws Exception {
        UserDTO userWithPersonalInfo = userService.getUserWithPersonalInfoByFullName(
                personalInfoForm.getName(),
                personalInfoForm.getLastName(),
                personalInfoForm.getMaternalLastName()
        );
        return ResponseEntity.ok().body(userWithPersonalInfo);
    }

    @GetMapping(path = "/control/number/{numControl}")
    public ResponseEntity<UserDTO> getUserByNumControl(@PathVariable("numControl") String numControl) throws Exception {
        UserDTO userDTO = userService.getUserByNumControl(numControl);
        return ResponseEntity.ok().body(userDTO);
    }
}