package com.example.demo.admin.domain.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.admin.domain.model.BloodtypeDto;
import com.example.demo.admin.repository.BloodtypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BloodtypeService {

  private final BloodtypeRepository repository;

  public List<BloodtypeDto> getBloodtypeList() {
    return repository.findAll();
  }

}
