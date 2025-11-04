package com.udemy.wallet.service;

import com.udemy.wallet.entity.Wallet;
import com.udemy.wallet.entity.WalletItem;
import com.udemy.wallet.repository.WalletItemRepository;
import com.udemy.wallet.util.enums.TypeEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Testes do Serviço de WalletItem")
public class WalletItemServiceTest {

    @MockBean
    WalletItemRepository walletItemRepository;

    @Autowired
    WalletItemService walletItemService;

    private static final Date DATE = new Date();
    private static final TypeEnum TYPE = TypeEnum.EN;
    private static final String DESCRIPTION = "Conta de luz";
    private static final BigDecimal AMOUNT = BigDecimal.valueOf(120);

    @Test
    @DisplayName("Deve salvar um item de carteira com sucesso")
    public void testSave() {
        // ARRANGE
        BDDMockito.given(walletItemRepository.save(Mockito.any(WalletItem.class))).willReturn(getMockWalletItem());

        // ACT
        WalletItem response = walletItemService.save(new WalletItem());

        // ASSERT
        assertNotNull(response);
        assertEquals(DESCRIPTION, response.getDescription());
        assertEquals(0, response.getAmount().compareTo(AMOUNT));
    }

    @Test
    @DisplayName("Deve buscar itens de carteira entre datas")
    public void testFindBetweenDates() {
        // ARRANGE
        List<WalletItem> list = new ArrayList<>();
        list.add(getMockWalletItem());
        Page<WalletItem> page = new PageImpl<>(list);

        BDDMockito.given(walletItemRepository.findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(Mockito.anyLong(), Mockito.any(Date.class), Mockito.any(Date.class), Mockito.any(PageRequest.class))).willReturn(page);

        // ACT
        Page<WalletItem> response = walletItemService.findBetweenDates(1L, new Date(), new Date(), 0);

        // ASSERT
        assertNotNull(response);
        assertEquals(1, response.getContent().size());
        assertEquals(DESCRIPTION, response.getContent().get(0).getDescription());
    }

    @Test
    public void testFindByTypes() {
        // ARRANGE
        List<WalletItem> list = new ArrayList<>();
        list.add(getMockWalletItem());

        BDDMockito.given(walletItemRepository.findByWalletIdAndType(Mockito.anyLong(), Mockito.any(TypeEnum.class))).willReturn(list);

        // ACT
        List<WalletItem> response = walletItemService.findByTypes(1L, TypeEnum.EN);

        // ASSERT
        assertNotNull(response);
        assertEquals(TYPE, response.get(0).getType());
    }

    @Test
    public void testSumByWallet() {
        // ARRANGE
        BigDecimal amount = BigDecimal.valueOf(50);

        BDDMockito.given(walletItemRepository.sumByWalletId(Mockito.anyLong())).willReturn(amount);

        // ACT
        BigDecimal response = walletItemService.sumByWalletId(1L);

        // ASSERT
        assertNotNull(response);
        assertEquals(0, response.compareTo(amount));
    }

    private WalletItem getMockWalletItem() {
        Wallet wallet = new Wallet();
        wallet.setId(1L);

        WalletItem walletItem = new WalletItem(1L, wallet, DATE, TYPE, DESCRIPTION, AMOUNT);

        return walletItem;
    }

}
