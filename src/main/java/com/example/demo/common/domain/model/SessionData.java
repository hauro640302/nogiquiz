package com.example.demo.common.domain.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import com.example.demo.menu.form.MenuForm;
import lombok.Data;

@Component
@SessionScope
@Data
public class SessionData {
  private String category;
  private boolean isActive;
  
  public void updateFromMenuForm(MenuForm form) {
    this.setCategory(form.category());
    this.setActive(!form.includingGraduates());
    // 現役生のみ == !卒業生を含む
  }
}
