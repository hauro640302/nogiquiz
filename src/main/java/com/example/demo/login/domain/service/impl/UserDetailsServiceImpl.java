package com.example.demo.login.domain.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.demo.login.domain.model.PlayerDto;
import com.example.demo.login.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

  private final PlayerRepository repository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    PlayerDto player = repository.findByPlayerName(username);
    if (player == null) {
      throw new UsernameNotFoundException("No such player");
    }

    GrantedAuthority auth = new SimpleGrantedAuthority(player.role());
    List<GrantedAuthority> auths = new ArrayList<>();
    auths.add(auth);
    UserDetails userDetails = new User(player.name(), player.password(), auths);

    return userDetails;
  }

}
