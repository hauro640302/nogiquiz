package com.example.demo.common.domain.model;

public record MemberDetailDto(
    Long id,
    String name,
    String birthdayYMD,
    String birthdayMD,
    String age,
    String bloodtype,
    String prefecture,
    String generation,
    Boolean isActive,
    String activeName) {
}
