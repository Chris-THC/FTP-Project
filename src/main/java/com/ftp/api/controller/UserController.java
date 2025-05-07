package com.ftp.api.controller;

import com.ftp.api.dto.UserDTO;
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
}
