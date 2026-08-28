package com.example.demo.admin.domain.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.admin.domain.model.GenerationDto;
import com.example.demo.admin.repository.GenerationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenerationService {
  
  private final GenerationRepository repository;
  
  public List<GenerationDto> getGenerationList() {
    return repository.findAll();
  }
  

}
