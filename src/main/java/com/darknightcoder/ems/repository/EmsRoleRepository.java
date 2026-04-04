package com.darknightcoder.ems.repository;

import com.darknightcoder.ems.entity.EmsRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmsRoleRepository extends JpaRepository<EmsRole,Long> {
    Optional<EmsRole> findByRoleType(String roleType);
}
