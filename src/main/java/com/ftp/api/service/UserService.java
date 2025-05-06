package com.ftp.api.service;

import com.ftp.api.dto.UserDTO;
import com.ftp.api.entity.User;
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

    public List<UserDTO> getAllUsers() {
        final List<User> getAll = userRepository.findAll();
        return getAll
                .stream()
                .map(UserDTO::build)
                .sorted(Comparator.comparing(UserDTO::getIdUser).reversed())
                .toList();
    }
}
