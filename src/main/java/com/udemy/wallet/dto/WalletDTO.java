package com.udemy.wallet.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Data
public class WalletDTO {

    private Long id;

    @NotNull(message = "O nome não pode ser nulo")
    @Length(min = 3, max = 80, message = "O nome deve conter entre 3 e 80 caracteres")
    private String name;

    @NotNull(message = "Insira um valor para a carteira")
    private BigDecimal amount;
}
