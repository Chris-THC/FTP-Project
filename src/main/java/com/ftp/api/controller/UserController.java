package com.ftp.api.controller;

import com.ftp.api.dto.UserDTO;
import com.ftp.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        final List<UserDTO> userDTOList = userService.getAllUsers();
        return ResponseEntity.ok().body(userDTOList);
    }
}
