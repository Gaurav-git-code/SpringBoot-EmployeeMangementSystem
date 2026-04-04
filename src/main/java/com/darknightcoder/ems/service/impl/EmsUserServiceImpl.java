package com.darknightcoder.ems.service.impl;

import com.darknightcoder.ems.entity.EmsRole;
import com.darknightcoder.ems.entity.EmsUser;
import com.darknightcoder.ems.exception.ResourceNotFoundException;
import com.darknightcoder.ems.model.EmsUserDto;
import com.darknightcoder.ems.repository.EmsRoleRepository;
import com.darknightcoder.ems.repository.EmsUserRepository;
import com.darknightcoder.ems.service.EmsUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class EmsUserServiceImpl implements EmsUserService {
    EmsUserRepository emsUserRepository;
    EmsRoleRepository emsRoleRepository;
    PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void createUser(EmsUserDto emsUserDto) {
        EmsUser emsUser = new EmsUser();
        emsUser.setUsername(emsUserDto.getUsername());
        emsUser.setPassword(passwordEncoder.encode(emsUserDto.getPassword()));
        emsUser.setEmail(emsUserDto.getEmail());
        EmsRole role =emsRoleRepository.findByRoleType(emsUserDto.getRoleType().name()).orElseThrow(
                () -> new ResourceNotFoundException("Role","Role_Type",emsUserDto.getRoleType().name()));
        Set<EmsRole> roles = new HashSet<>();
        roles.add(role);
        emsUser.setRoles(roles);
        emsUserRepository.save(emsUser);
    }

}
