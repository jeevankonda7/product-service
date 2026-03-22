package com.nerdcoder.jeelax.repository;

import com.nerdcoder.jeelax.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<Users, Integer> {

  Optional<Users> findByUsername(String username);
}
