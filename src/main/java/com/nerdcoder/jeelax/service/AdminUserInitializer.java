package com.nerdcoder.jeelax.service;

import com.nerdcoder.jeelax.entity.Users;
import com.nerdcoder.jeelax.repository.UserDetailsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminUserInitializer {

  @Bean
  public CommandLineRunner createAdminUser(final UserDetailsRepository userDetailsRepository,
                                           final PasswordEncoder passwordEncoder) {
    return args -> {
      if (userDetailsRepository.findByUsername("admin").isEmpty()) {
        final Users adminUser = Users
                .builder()
                .username("admin")
                .password(passwordEncoder.encode("admin@1703"))
                .role("ROLE_ADMIN")
                .build();
        userDetailsRepository.save(adminUser);
        System.out.println("Admin profile is created");

      }

    };
  }
}
