package com.ddib.notification.user.service;

import com.ddib.notification.user.domain.User;
import com.ddib.notification.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findByUserId(Integer userId) {
        return userRepository.findByUserId(userId);
    }
}
