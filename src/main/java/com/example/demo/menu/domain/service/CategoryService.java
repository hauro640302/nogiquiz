package com.example.demo.menu.domain.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.common.domain.model.CategoryDto;
import com.example.demo.common.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
  
  private final CategoryRepository repository;
  
  public List<String> getCategories() {
    return repository.findAll().stream().map(CategoryDto::name).toList();
  }

  public String getDefaultCategory( ) {
    return getCategories().get(0);
  }
  
  public boolean hasCategory(String categoryName) {
    return getCategories().contains(categoryName);
  }
}
