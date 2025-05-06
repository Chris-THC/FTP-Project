package com.ftp.api.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum Role {
    Admin(0), Professor(1), Student(1);
    private final int key;

    public static Role getRole(Integer key) {
        Map<Integer, Role> roleMap =
                Arrays.stream(Role.values()).collect(Collectors.toMap(Role::getKey, Function.identity()));
        return roleMap.get(key);
    }
}