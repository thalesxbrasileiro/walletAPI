package com.udemy.wallet.impl;

import com.udemy.wallet.entity.UserWallet;
import com.udemy.wallet.repository.UserWalletRepository;
import com.udemy.wallet.service.UserWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserWalletServiceImpl implements UserWalletService {

    @Autowired
    private UserWalletRepository userWalletRepository;

    @Override
    public UserWallet save(UserWallet userWallet) {
        return userWalletRepository.save(userWallet);
    }

}
