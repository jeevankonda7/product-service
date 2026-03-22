package com.nerdcoder.jeelax.service;

import com.nerdcoder.jeelax.entity.Users;
import com.nerdcoder.jeelax.repository.UserDetailsRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersCheckInService {

  private final UserDetailsRepository usersRepository;
  private final EmailTransferService emailTransferService;
  private final PasswordEncoder passwordEncoder;

  public UsersCheckInService(UserDetailsRepository usersRepository,
                             EmailTransferService emailTransferService,
                             PasswordEncoder passwordEncoder) {
    this.usersRepository = usersRepository;
    this.emailTransferService = emailTransferService;
    this.passwordEncoder = passwordEncoder;
  }

  public Integer addUser(final Users user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    final Users savedUser = usersRepository.save(user);
    if (savedUser.getId()>0) {
     this.emailTransferService.sendEmail(savedUser.getEmail());
    }
    return savedUser.getId();
  }

  public Users getUser(final Users user) {
    return usersRepository.findByUsername(user.getUsername()).get();
  }
}
