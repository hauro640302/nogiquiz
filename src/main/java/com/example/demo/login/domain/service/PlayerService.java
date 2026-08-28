package com.example.demo.login.domain.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.login.domain.model.PlayerDto;
import com.example.demo.login.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayerService {

  private final PlayerRepository repository;
  private final PasswordEncoder encoder;
  private final MessageSource messageSource;

  public void addPlayer(String playerName, String password) {

    if (repository.findByPlayerName(playerName) != null) {
      Locale locale = LocaleContextHolder.getLocale();
      String message = messageSource.getMessage("playerservice.addplayer.duplicate",
          List.of(playerName).toArray(), locale);
      throw new DuplicateKeyException(message);
    }

    final String encodedPw = encoder.encode(password);
    PlayerDto player = new PlayerDto(0L, playerName, encodedPw, null, "GENERAL");

    repository.addPlayer(player);
  }

  public Duration getLoginDuration(String playerName) {
    PlayerDto player = repository.findByPlayerName(playerName);
    LocalDateTime now = LocalDateTime.now();
    return Duration.between(
        player.loggedinAt() != null ? player.loggedinAt() : now, now);
  }

}
