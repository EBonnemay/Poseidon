package com.nnk.springboot.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserPrincipal implements UserDetails {
    /**
     UserPrincipal is an implementation of the UserDetails interface that wraps a User object.
     It provides methods to get user credentials and authorities required for authentication and authorization purposes.
     */
    private User user;


    public UserPrincipal(User user) {
        this.user = user;
    }
    /**

     This method returns a collection of granted authorities (like ROLE_ADMIN or ROLE_USER) for the user.
     @return a collection of granted authorities for the user.

     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        String userRole = user.getRole(); // get the user's role as a string
        authorities.add(new SimpleGrantedAuthority(userRole));
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
