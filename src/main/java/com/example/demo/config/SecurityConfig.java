package com.example.demo.config;

import java.util.Arrays;
import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity(debug = false)
public class SecurityConfig {

  private final Environment env;

  @Bean
  PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http,
      AuthenticationSuccessHandler handler) throws Exception {

    if (Arrays.asList(env.getActiveProfiles()).contains("dev")) {
      http
          .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
          .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
          .authorizeHttpRequests(auth -> auth.requestMatchers("/h2-console/**").permitAll());
    }

    http
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
            .requestMatchers("/", "/login/", "/login/**").permitAll()
            .requestMatchers("/logout").permitAll()
            .requestMatchers("/error").permitAll()
            .requestMatchers("/admin").hasAuthority("SUPERUSER")
            .anyRequest().authenticated())
        .formLogin(login -> login
            .loginPage("/login")
            .usernameParameter("name")
            .passwordParameter("password")
            .defaultSuccessUrl("/menu")
            .failureUrl("/login?error")
            .successHandler(handler)
            .permitAll())
        .logout(logout -> logout
            .logoutUrl("/login/logout")
            .logoutSuccessUrl("/login?logout")
            .invalidateHttpSession(true).deleteCookies("JSESSIONID"));

    return http.build();
  }

}
