package com.who.shuk.JavaEmail.service;

import com.who.shuk.JavaEmail.model.User;

public interface UserService {
    User saveUser(User user);
    Boolean verifyToken(String token);
}
