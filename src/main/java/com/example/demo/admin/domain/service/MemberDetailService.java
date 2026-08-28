package com.example.demo.admin.domain.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.demo.common.domain.model.MemberDetailDto;
import com.example.demo.common.repository.MemberDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberDetailService {

  private final MemberDetailRepository repository;

  public Page<MemberDetailDto> getMemberDetail(Pageable pageable) {

    List<MemberDetailDto> md =
        repository.findAllByPage(pageable.getPageNumber(), pageable.getPageSize());

    int count = repository.count();

    return new PageImpl<MemberDetailDto>(md, pageable, count);
  }
}
