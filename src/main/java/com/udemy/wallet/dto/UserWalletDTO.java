package com.udemy.wallet.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserWalletDTO {

    private Long id;

    @NotNull(message = "Informe o id do usuário")
    private Long wallet;

    @NotNull(message = "Informe o id da carteira")
    private Long users;
}
