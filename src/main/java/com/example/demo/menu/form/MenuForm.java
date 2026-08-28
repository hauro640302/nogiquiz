package com.example.demo.menu.form;

import com.example.demo.common.domain.model.SessionData;

public record MenuForm(
    String category,
    boolean includingGraduates) {

  public static MenuForm fromSesionData(SessionData sd) {
    return new MenuForm(sd.getCategory(), !sd.isActive());
  }
}
