package com.example.demo.admin.form;

import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import org.springframework.format.annotation.DateTimeFormat;
import com.example.demo.admin.domain.model.MemberDto;

public record MemberForm(
    long id,
    @NotBlank String name,
    @DateTimeFormat(pattern = "yyyy-MM-dd") 
    @Past 
    LocalDate birthday,
    long bloodtypeId,
    long prefectureId,
    long generationId,
    boolean active) {

  public static MemberForm fromDto(MemberDto dto) {
    return new MemberForm(
        dto.id(),
        dto.name(),
        dto.birthday(),
        dto.bloodtypeId(),
        dto.prefectureId(),
        dto.generationId(),
        dto.isActive());
  }

}
