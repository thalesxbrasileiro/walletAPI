package com.udemy.wallet.impl;

import com.udemy.wallet.entity.Wallet;
import com.udemy.wallet.repository.WalletRepository;
import com.udemy.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public Wallet save(Wallet wallet) {
        return walletRepository.save(wallet);
    }

}
