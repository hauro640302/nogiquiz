package com.example.demo.menu.controller;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.demo.common.domain.model.SessionData;
import com.example.demo.menu.domain.model.HighScoreDto;
import com.example.demo.menu.domain.service.CategoryService;
import com.example.demo.menu.domain.service.HighScoreService;
import com.example.demo.menu.form.HighScoreForm;
import com.example.demo.menu.form.MenuForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MenuController {

  private final CategoryService categoryService;
  private final HighScoreService highScoreService;
  private final SessionData sessionData;

  // メニュー画面
  @GetMapping("/menu")
  public String getMenu(Model model) {
    // セッションデータが未設定であれば初期値を入れる
    if (sessionData.getCategory() == null) {
      sessionData.setCategory(categoryService.getDefaultCategory());
      sessionData.setActive(true);
    }

    // カテゴリ一覧取得
    List<String> categories = categoryService.getCategories();

    // カテゴリなどに対応したハイスコアを取得
    Integer page = (Integer) model.getAttribute("page");
    Pageable pageable = PageRequest.of(page == null ? 0 : page, 10);
    Page<HighScoreDto> hs = highScoreService.getHighScore(
        sessionData.getCategory(), sessionData.isActive(), pageable);
    List<HighScoreForm> highScores =
        hs.getContent().stream().map(HighScoreForm::fromDto).toList();

    model.addAttribute("categories", categories);
    model.addAttribute("highScores", highScores);
    model.addAttribute("pageable", hs);
    model.addAttribute("menuForm", MenuForm.fromSesionData(sessionData));

    return "menu/menu";
  }

  @PostMapping("/menu")
  public String postMenu() {
    return "redirect:/quiz";
  }

  @PostMapping("/menu/change")
  public String postMenuChange(MenuForm menuForm) {

    sessionData.updateFromMenuForm(menuForm);

    return "redirect:/menu";
  }

  @PostMapping("/menu/page")
  public String postMenuPage(@RequestParam(required = false) Integer page,
      RedirectAttributes redirectAttributes) {

    redirectAttributes.addFlashAttribute("page", page);

    return "redirect:/menu";
  }

}
