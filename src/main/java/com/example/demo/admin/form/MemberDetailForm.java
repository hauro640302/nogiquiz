package com.example.demo.admin.form;

import com.example.demo.common.domain.model.MemberDetailDto;

public record MemberDetailForm(
    Long id,
    String name,
    String birthday,
    String bloodtype,
    String prefecture,
    String generation,
    String active) {

  public static MemberDetailForm fromDto(MemberDetailDto dto) {
    return new MemberDetailForm(
        dto.id(),
        dto.name(),
        dto.birthdayYMD(),
        dto.bloodtype(),
        dto.prefecture(),
        dto.generation(),
        dto.activeName());
  }
}
