package com.example.demo.menu.domain.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.demo.menu.domain.model.HighScoreDto;
import com.example.demo.menu.repository.HighScoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class HighScoreService {

  private final HighScoreRepository repository;

  public Page<HighScoreDto> getHighScore(String categoryName, boolean isActive,
      Pageable pageable) {

    List<HighScoreDto> hs = repository.getHighScoreByPage(categoryName, isActive,
        pageable.getPageNumber(), pageable.getPageSize());

    int count = repository.count(categoryName, isActive);

    return new PageImpl<HighScoreDto>(hs, pageable, count);
  }
}
