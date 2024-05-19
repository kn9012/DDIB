package com.ddib.notification.user.service;

import com.ddib.notification.user.domain.User;
import org.springframework.stereotype.Service;


@Service
public interface UserService {
    User findByUserId(Integer userId);
}
