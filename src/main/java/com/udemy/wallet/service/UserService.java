package com.udemy.wallet.service;

import com.udemy.wallet.entity.User;

import java.util.Optional;

public interface UserService {

    User save(User user);

    Optional<User> findByEmail(String email);
}
