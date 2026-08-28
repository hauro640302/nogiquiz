package com.example.demo.admin.domain.service;

import java.time.LocalDate;
import org.springframework.stereotype.Service;
import com.example.demo.admin.domain.model.MemberDto;
import com.example.demo.admin.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

  private final MemberRepository repository;

  public MemberDto getMember(long id) {
    return repository.findById(id);
  }

  public MemberDto getNewMember() {
    // オーディションの最小年齢は12歳
    return new MemberDto(0L, "", LocalDate.now().minusYears(12), 0L, 0L, 0L, true);
  }

  public boolean updateMember(MemberDto member) {
    MemberDto original = getMember(member.id());
    return member.equals(original) ? true : repository.update(member);
  }

  public boolean addMember(MemberDto member) {
    return repository.add(member);
  }
}
