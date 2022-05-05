package com.tisory.jaimemin.jwt.service;

import com.tisory.jaimemin.jwt.entity.User;
import com.tisory.jaimemin.jwt.dto.UserDto;
import com.tisory.jaimemin.jwt.entity.Authority;
import com.tisory.jaimemin.jwt.repository.UserRepository;
import com.tisory.jaimemin.jwt.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

/**
 * @author jaime
 * @title UserService
 * @see\n <pre>
 * </pre>
 * @since 2022-05-05
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User signup(UserDto userDto) {
        if (getUser(userDto) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(String username) {
        return userRepository.findOneWithAuthoritiesByUsername(username);
    }

    @Transactional(readOnly = true)
    public Optional<User> getMyUserWithAuthorities() {
        return SecurityUtil.getCurrentUsername()
                .flatMap(userRepository::findOneWithAuthoritiesByUsername);
    }

    private com.tisory.jaimemin.jwt.entity.User getUser(UserDto userDto) {
        return userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername())
                .orElse(null);
    }
}
