package com.udemy.wallet.repository;

import com.udemy.wallet.entity.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    private static final String EMAIL = "email@email.com";

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        User user = new User();
        user.setName("user");
        user.setPassword("senha12345");
        user.setEmail(EMAIL);

        userRepository.save(user);
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void testSave() {
        User user = new User();
        user.setName("Teste");
        user.setPassword("12345");
        user.setEmail("teste@teste.com");

        User response = userRepository.save(user);

        assertNotNull(response);
    }

    @Test
    public void testFindByEmail() {
        Optional<User> response = userRepository.findByEmail(EMAIL);

        assertTrue(response.isPresent());
        assertEquals(EMAIL, response.get().getEmail());
    }

}
