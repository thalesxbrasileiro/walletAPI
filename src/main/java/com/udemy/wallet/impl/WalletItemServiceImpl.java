package com.udemy.wallet.impl;

import com.udemy.wallet.entity.WalletItem;
import com.udemy.wallet.repository.WalletItemRepository;
import com.udemy.wallet.service.WalletItemService;
import com.udemy.wallet.util.enums.TypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class WalletItemServiceImpl implements WalletItemService {

    @Autowired
    WalletItemRepository walletItemRepository;

    @Value("${pagination.items_per_page}")
    private int itemsPerPage;

    @Override
    public WalletItem save(WalletItem walletItem) {
        return walletItemRepository.save(walletItem);
    }

    @Override
    public Page<WalletItem> findBetweenDates(Long wallet, Date start, Date end, int pageNumber) {
        PageRequest page = PageRequest.of(pageNumber, itemsPerPage);

        return walletItemRepository.findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(wallet, start, end, page);
    }

    @Override
    public List<WalletItem> findByTypes(Long wallet, TypeEnum type) {
        return walletItemRepository.findByWalletIdAndType(wallet, type);
    }

    @Override
    public BigDecimal sumByWalletId(Long walletId) {
        return walletItemRepository.sumByWalletId(walletId);
    }

}
