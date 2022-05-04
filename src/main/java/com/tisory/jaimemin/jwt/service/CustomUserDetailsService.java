package com.tisory.jaimemin.jwt.service;

import com.tisory.jaimemin.jwt.entity.User;
import com.tisory.jaimemin.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jaime
 * @title CustomUserDetailsService
 * @see\n <pre>
 * </pre>
 * @since 2022-05-04
 */
@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findOneWithAuthoritiesByUsername(username)
                .map(user -> createUser(username, user))
                .orElseThrow(() -> new UsernameNotFoundException(username + " -> can't find in database"));
    }

    private UserDetails createUser(String username, User user) {
        if (!user.isActivated()) {
            throw new RuntimeException(username + " -> is not activated");
        }

        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority((authority.getAuthorityName())))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getUsername()
                , user.getPassword()
                , grantedAuthorities);
    }
}
