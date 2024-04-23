package com.ddib.notification.user.service;

import com.ddib.notification.user.domain.User;
import com.ddib.notification.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findByEmail(Principal principal) {
        return userRepository.findByEmail(principal.getName());
    }
}
