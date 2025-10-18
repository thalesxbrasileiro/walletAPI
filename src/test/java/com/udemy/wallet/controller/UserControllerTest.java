package com.udemy.wallet.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udemy.wallet.dto.UserDto;
import com.udemy.wallet.entity.User;
import com.udemy.wallet.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DisplayName("Testes do Controller de Usuário")
public class UserControllerTest {

    private static final Long ID = 1L;
    private static final String NAME = "User Test";
    private static final String PASSWORD = "123456";
    private static final String EMAIL = "email@test.com";
    private static final String URL = "/user";

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("Deve salvar um usuário com sucesso")
    public void testSave() throws Exception {

        BDDMockito.given(userService.save(Mockito.any(User.class))).willReturn(getMockUser());

        mvc.perform(MockMvcRequestBuilders.post(URL)
                .content(getJsonPayload(ID, NAME, PASSWORD, EMAIL))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.data.id").value(ID))
        .andExpect(jsonPath("$.data.name").value(NAME))
        .andExpect(jsonPath("$.data.password").doesNotExist())
        .andExpect(jsonPath("$.data.email").value(EMAIL));
    }

    @Test
    @DisplayName("Deve retornar erro de validação ao tentar salvar um usuário com email inválido")
    public void testSaveInvalidUser() throws Exception {

        mvc.perform(MockMvcRequestBuilders.post(URL)
                        .content(getJsonPayload(ID, NAME, PASSWORD, "email"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errors[0]").value("Email inválido"));
    }

    public User getMockUser() {
        User user = new User();
        user.setId(ID);
        user.setName(NAME);
        user.setPassword(PASSWORD);
        user.setEmail(EMAIL);

        return user;
    }

    public String getJsonPayload(Long id, String nome, String password, String email) throws JsonProcessingException {
        UserDto dto = new UserDto();
        dto.setId(id);
        dto.setName(nome);
        dto.setPassword(password);
        dto.setEmail(email);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(dto);
    }

}
