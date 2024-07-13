package com.semicolon.africa.Estore.services;

import com.semicolon.africa.Estore.data.repositories.Admins;
import com.semicolon.africa.Estore.data.repositories.Customers;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final Admins admins;
    private final Customers customers;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = admins.findByEmail(username);
        if(userDetails == null) userDetails = customers.findByEmail(username);
        if(userDetails == null) throw new UsernameNotFoundException("User Not Found");
        return userDetails;
    }
}
