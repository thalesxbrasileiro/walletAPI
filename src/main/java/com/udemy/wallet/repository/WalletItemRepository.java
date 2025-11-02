package com.udemy.wallet.repository;

import com.udemy.wallet.entity.WalletItem;
import com.udemy.wallet.util.enums.TypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface WalletItemRepository extends JpaRepository<WalletItem, Long> {
    Page<WalletItem> findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(Long walletId, Date init, Date end, Pageable page);
    List<WalletItem> findByWalletIdAndType(Long walletId, TypeEnum type);

    @Query(value = "select sum(amount) from WalletItem wi where wi.wallet.id = :walletId")
    BigDecimal sumByWalletId(@Param("walletId") Long walletId);
}
