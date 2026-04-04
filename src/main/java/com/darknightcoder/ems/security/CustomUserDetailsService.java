package com.darknightcoder.ems.security;

import com.darknightcoder.ems.entity.EmsUser;
import com.darknightcoder.ems.exception.ResourceNotFoundException;
import com.darknightcoder.ems.repository.EmsUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private EmsUserRepository emsUserRepository;


    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        EmsUser user = emsUserRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(
                () -> new ResourceNotFoundException("User","Username", usernameOrEmail));

    List<GrantedAuthority> authorityList = user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getRoleType()))
            .collect(Collectors.toList());

        return new EmsUserDetails(user, authorityList);
    }
}
