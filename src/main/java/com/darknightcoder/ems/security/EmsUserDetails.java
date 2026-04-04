package com.darknightcoder.ems.security;

import com.darknightcoder.ems.entity.EmsUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class EmsUserDetails implements UserDetails {
    private EmsUser user;
    private List<GrantedAuthority> authorities;
    public EmsUserDetails(EmsUser user,List<GrantedAuthority> authorities){
        this.user = user;
        this.authorities= authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }
}
