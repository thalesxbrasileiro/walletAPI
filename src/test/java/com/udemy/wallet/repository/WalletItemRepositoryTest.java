package com.udemy.wallet.repository;

import com.udemy.wallet.entity.Wallet;
import com.udemy.wallet.entity.WalletItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Testes do Repositório de WalletItem")
public class WalletItemRepositoryTest {

    private static Date DATE = new Date();
    private static String TYPE = "EN";
    private static String DESCRIPTION = "Conta de Luz";
    private static BigDecimal AMOUNT = BigDecimal.valueOf(65);

    @Autowired
    WalletItemRepository walletItemRepository;

    @Autowired
    WalletRepository walletRepository;

    @Test
    @DisplayName("Deve salvar um item de carteira com sucesso")
    public void testSave() {

        Wallet wallet = new Wallet();
        wallet.setName("Carteira 1");
        wallet.setAmount(BigDecimal.valueOf(200));

        walletRepository.save(wallet);

        WalletItem walletItem = new WalletItem(null, wallet, DATE, TYPE, DESCRIPTION, AMOUNT) ;

        WalletItem response = walletItemRepository.save(walletItem);

        assertNotNull(response);
        assertEquals(DATE, response.getDate());
        assertEquals(TYPE, response.getType());
        assertEquals(DESCRIPTION, response.getDescription());
        assertEquals(AMOUNT, response.getAmount());
    }

}
