package com.example.demo.admin.domain.model;

import java.time.LocalDate;
import com.example.demo.admin.form.MemberForm;

public record MemberDto(
    Long id,
    String name,
    LocalDate birthday,
    Long bloodtypeId,
    Long prefectureId,
    Long generationId,
    Boolean isActive) {
  
  public static MemberDto fromForm(MemberForm form) {
    return new MemberDto(
        form.id(),
        form.name(),
        form.birthday(),
        form.bloodtypeId(),
        form.prefectureId(),
        form.generationId(),
        form.active());
  }
}
