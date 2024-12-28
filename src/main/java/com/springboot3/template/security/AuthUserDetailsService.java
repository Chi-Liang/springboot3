package com.springboot3.template.security;

import com.springboot3.template.model.entity.User;
import com.springboot3.template.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findOneByNameIgnoreCase(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("");
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.get().getName())
                .password(user.get().getPassword())
                .authorities(createAuthorities(user.get()))
                .build();
    }

    private String[] createAuthorities(User user) {
        return new String[]{user.getRole()};
    }
}
