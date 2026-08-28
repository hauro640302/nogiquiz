package com.example.demo.login.domain.model;

import java.time.LocalDateTime;

public record PlayerDto(
    Long id,
    String name,
    String password,
    LocalDateTime loggedinAt,
    String role) {
}
