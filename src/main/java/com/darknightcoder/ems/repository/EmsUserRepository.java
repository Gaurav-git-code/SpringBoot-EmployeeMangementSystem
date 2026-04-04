package com.darknightcoder.ems.repository;

import com.darknightcoder.ems.entity.EmsUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmsUserRepository extends JpaRepository<EmsUser,Long> {
    Optional<EmsUser> findByUsernameOrEmail(String username, String email);
}
