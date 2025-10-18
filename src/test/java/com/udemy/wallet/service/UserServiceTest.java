package com.udemy.wallet.service;

import com.udemy.wallet.entity.User;
import com.udemy.wallet.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Testes do Serviço de Usuário")
public class UserServiceTest {

    @MockBean
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @BeforeEach
    public void setUp() {
        BDDMockito.given(userRepository.findByEmail(Mockito.anyString())).willReturn(Optional.of(new User()));
    }

    @Test
    @DisplayName("Deve buscar um usuário pelo email")
    public void testFindByEmail() {
        Optional<User> user = userService.findByEmail("email@test.com");

        assertTrue(user.isPresent());
    }


}
