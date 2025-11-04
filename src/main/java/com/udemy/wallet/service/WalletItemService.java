package com.udemy.wallet.service;

import com.udemy.wallet.entity.WalletItem;
import com.udemy.wallet.util.enums.TypeEnum;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface WalletItemService {

    WalletItem save(WalletItem walletItem);
    Page<WalletItem> findBetweenDates(Long wallet, Date start, Date end, int Page);
    List<WalletItem> findByTypes(Long wallet, TypeEnum type);
    BigDecimal sumByWalletId(Long walletId);
}
