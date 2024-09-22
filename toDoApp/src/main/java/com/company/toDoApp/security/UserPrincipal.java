package com.company.toDoApp.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserPrincipal implements UserDetails {

    private int id;
    private String mail;
    private transient  String password;
    private GrantedAuthority authority; // Bir rol

    public UserPrincipal(){

    }
    public UserPrincipal(int id, String mail, String password, String role) {
        this.id = id;
        this.mail = mail;
        this.password = password;
        this.authority = new SimpleGrantedAuthority(role); // Bazadan alınan rol
    }



    public GrantedAuthority getAuthority(String role) {
        return new SimpleGrantedAuthority(role);
    }
    public int getId(){
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public GrantedAuthority getAuthority(){
        return  authority;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return mail;
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
