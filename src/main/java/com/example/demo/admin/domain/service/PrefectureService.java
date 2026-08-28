package com.example.demo.admin.domain.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.admin.domain.model.PrefectureDto;
import com.example.demo.admin.repository.PrefectureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrefectureService {
  
  private final PrefectureRepository repository;
  
  public List<PrefectureDto> getPrefectureList() {
    return repository.findAll();
  }
  
}
