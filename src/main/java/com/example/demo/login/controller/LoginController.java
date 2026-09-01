package com.example.demo.login.controller;

import java.time.Duration;
import java.util.List;
import java.util.Locale;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.demo.login.domain.service.PlayerService;
import com.example.demo.login.form.LogoutForm;
import com.example.demo.login.form.PlayerForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

  private final PlayerService service;
  private final MessageSource messageSource;

  // ログイン画面
  @GetMapping({
      "/", "/login"
  })
  public String getLogin(@ModelAttribute PlayerForm playerForm, HttpSession session,
      Model model) {

    AuthenticationException ex =
        (AuthenticationException) session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");

    boolean hasLoginError = ex != null;
    model.addAttribute("hasLoginError", hasLoginError);

    if (hasLoginError) {
      session.removeAttribute("SPRING_SECURITY_LAST_EXCEPTION");
    }

    return "login/login";
  }

  @PostMapping("/login")
  public String postLogin() {
    return "redirect:/menu";
  }

  // プレーヤー登録画面
  @GetMapping("/login/register")
  public String getRegister(Model model) {

    if (!model.containsAttribute("playerForm")) {
      // PlayerForm を @ModelAttribute すると、リダイレクト前のバリデーション情報が
      // 消えてしまうので、このように書かざるを得ない
      model.addAttribute("playerForm", new PlayerForm());
    }

    return "login/register";
  }

  @PostMapping("/login/register")
  public String postRegister(@Validated @ModelAttribute PlayerForm playerForm,
      BindingResult result, RedirectAttributes redirectAttributes) {

    if (result.hasErrors()) {
      playerForm.setResult(false);
      redirectAttributes.addFlashAttribute("playerForm", playerForm);
      redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "playerForm",
          result);

      return "redirect:/login/register";
    }

    try {
      service.addPlayer(playerForm.getName(), playerForm.getPassword());
    }
    catch (Exception e) {
      playerForm.setResult(false);
      playerForm.setMessage(e.getMessage());
      redirectAttributes.addFlashAttribute("playerForm", playerForm);
      return "redirect:/login/register";
    }

    Locale locale = LocaleContextHolder.getLocale();
    String message = messageSource.getMessage("logincontroller.postregister",
        List.of(playerForm.getName()).toArray(), locale);
    playerForm.setResult(true);
    playerForm.setMessage(message);
    redirectAttributes.addFlashAttribute("playerForm", playerForm);

    return "redirect:/login/register";
  }

  // ログアウト直前画面

  @GetMapping("/login/logout/pre")
  public String getPreLogout(@ModelAttribute LogoutForm logoutForm,
      HttpServletRequest request) {

    HttpSession session = request.getSession(false);

    if (session != null) {
      session.invalidate();
    }

    return "login/logout";
  }

  @PostMapping("/login/logout/pre")
  public String postPreLogout(@AuthenticationPrincipal UserDetails user,
      RedirectAttributes redirectAttributes) {

    Duration duration = service.getLoginDuration(user.getUsername());

    LogoutForm form = new LogoutForm(
        user.getUsername(),
        duration.toHours(), duration.toMinutesPart(), duration.toSecondsPart());
    redirectAttributes.addFlashAttribute("logoutForm", form);

    return "redirect:/login/logout/pre";
  }

}
