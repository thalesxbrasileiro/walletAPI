package com.udemy.wallet.repository;

import com.udemy.wallet.entity.Wallet;
import com.udemy.wallet.entity.WalletItem;
import com.udemy.wallet.util.enums.TypeEnum;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Testes do Repositório de WalletItem")
public class WalletItemRepositoryTest {

    private static Date DATE = new Date();
    private static TypeEnum TYPE = TypeEnum.EN;
    private static String DESCRIPTION = "Conta de Luz";
    private static BigDecimal AMOUNT = BigDecimal.valueOf(65);
    private Long savedWalletId = null;
    private Long savedWalletItemId = null;

    @Autowired
    WalletItemRepository walletItemRepository;

    @Autowired
    WalletRepository walletRepository;

    @BeforeEach
    public void setUp() {
        Wallet wallet = new Wallet();
        wallet.setName("Carteira Teste");
        wallet.setAmount(BigDecimal.valueOf(500));
        walletRepository.save(wallet);

        WalletItem walletItem = new WalletItem(null, wallet, DATE, TYPE, DESCRIPTION, AMOUNT) ;
        walletItemRepository.save(walletItem);

        savedWalletId = wallet.getId();
        savedWalletItemId = walletItem.getId();
    }

    @AfterEach
    public void tearDown() {
        walletItemRepository.deleteAll();
        walletRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve salvar um item de carteira com sucesso")
    public void testSave() {

        // ARRANGE
        Wallet wallet = new Wallet();
        wallet.setName("Carteira 1");
        wallet.setAmount(BigDecimal.valueOf(200));

        walletRepository.save(wallet);

        WalletItem walletItem = new WalletItem(null, wallet, DATE, TYPE, DESCRIPTION, AMOUNT) ;

        // ACT
        WalletItem response = walletItemRepository.save(walletItem);

        // ASSERT
        assertNotNull(response);
        assertEquals(DATE, response.getDate());
        assertEquals(TYPE, response.getType());
        assertEquals(DESCRIPTION, response.getDescription());
        assertEquals(AMOUNT, response.getAmount());
    }

    @Test
    @DisplayName("Deve lançar exceção ao salvar um item de carteira inválido (campos nulos)")
    public void testSaveInvalidWalletItem() {
        // ASSERT
        assertThrows(ConstraintViolationException.class, () -> {
            // ARRANGE
            WalletItem walletItem = new WalletItem(null, null, null, null, "Teste", null);
            // ACT
            walletItemRepository.save(walletItem);
        });
    }

    @Test
    @DisplayName("Deve atualizar a descrição de um item de carteira")
    public void testUpdate() {
        // ARRANGE
        WalletItem itemToUpdate = walletItemRepository.findById(savedWalletItemId)
                .orElseThrow(() -> new AssertionError("Item de carteira para atualização não foi encontrado."));

        String description = "Descrição alterada";

        itemToUpdate.setDescription(description);

        // ACT
        WalletItem updatedItem = walletItemRepository.save(itemToUpdate);

        // ASSERT
        assertEquals(description, updatedItem.getDescription());
    }

    @Test
    @DisplayName("Deve deletar um item de carteira")
    public void deletetWalletItem() {
        // ARRANGE
        Optional<Wallet> wallet = walletRepository.findById(savedWalletId);

        WalletItem walletItem = new WalletItem(null, wallet.get(), DATE, TYPE, DESCRIPTION, AMOUNT);
        walletItemRepository.save(walletItem);

        walletItemRepository.deleteById(walletItem.getId());

        // ACT
        Optional<WalletItem> response = walletItemRepository.findById(walletItem.getId());

        // ASSERT
        assertFalse(response.isPresent());
    }

    @Test
    @DisplayName("Deve buscar itens de carteira entre datas")
    public void testFindBetweenDates() {
        // ARRANGE
        Optional<Wallet> wallet = walletRepository.findById(savedWalletId);

        LocalDateTime localDateTime = DATE.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        Date currentDatePlusFiveDays = Date.from(localDateTime.plusDays(5).atZone(ZoneId.systemDefault()).toInstant());
        Date currentDatePlusSevenDays = Date.from(localDateTime.plusDays(7).atZone(ZoneId.systemDefault()).toInstant());

        walletItemRepository.save(new WalletItem(null, wallet.get(), currentDatePlusFiveDays, TYPE, DESCRIPTION, AMOUNT));
        walletItemRepository.save(new WalletItem(null, wallet.get(), currentDatePlusSevenDays, TYPE, DESCRIPTION, AMOUNT));

        PageRequest pageRequest = PageRequest.of(0, 10);

        // ACT
        Page<WalletItem> response = walletItemRepository.findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(savedWalletId, DATE, currentDatePlusFiveDays, pageRequest);

        // ASSERT
        assertEquals(2, response.getContent().size());
        assertEquals(2, response.getTotalElements());
        assertEquals(savedWalletId, response.getContent().get(0).getWallet().getId());
    }

    @Test
    @DisplayName("Deve buscar itens de carteira por tipo")
    public void testFindByType() {
        // ARRANGE & ACT
        List<WalletItem> response = walletItemRepository.findByWalletIdAndType(savedWalletId, TYPE);

        // ASSERT
        assertEquals(1, response.size());
        assertEquals(TYPE, response.get(0).getType());
    }

    @Test
    @DisplayName("Deve buscar itens de carteira por tipo saída")
    public void testFindByTypeSaida() {
        // ARRANGE
        Optional<Wallet> wallet = walletRepository.findById(savedWalletId);

        walletItemRepository.save(new WalletItem(null, wallet.get(), DATE, TypeEnum.SA, DESCRIPTION, AMOUNT));

        // ACT
        List<WalletItem> response = walletItemRepository.findByWalletIdAndType(savedWalletId, TypeEnum.SA);

        // ASSERT
        assertEquals(1, response.size());
        assertEquals(TypeEnum.SA, response.get(0).getType());
    }

    @Test
    @DisplayName("Deve somar os itens de uma carteira")
    public void testSumByWallet() {
        // ARRANGE
        Optional<Wallet> wallet = walletRepository.findById(savedWalletId);

        walletItemRepository.save(new WalletItem(null, wallet.get(), DATE, TYPE, DESCRIPTION, BigDecimal.valueOf(150.80)));

        // ACT
        BigDecimal response = walletItemRepository.sumByWalletId(savedWalletId);

        // ASSERT
        assertEquals(0, response.compareTo(BigDecimal.valueOf(215.80)));
    }

}
