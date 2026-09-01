package com.example.demo.login.domain.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.example.demo.login.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

  private final PlayerRepository repository;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication) throws IOException {

    // ログイン時に spring security から起動され、ログイン時刻をDBに書く
    String username = authentication.getName();
    repository.updatePlayerLoggedinAt(username,
        LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    
    response.sendRedirect("/menu");
  }

}
