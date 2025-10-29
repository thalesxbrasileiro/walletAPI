package com.udemy.wallet.dto;

import com.udemy.wallet.entity.Wallet;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class WalletItemDTO {

    private Long id;

    @NotNull(message = "Insira o id da carteira")
    private Wallet wallet;

    @NotNull(message = "Informe uma data")
    private Date date;

    @NotNull(message = "Informe um tipo")
    @Pattern(regexp = "^(ENTRADA|SAÍDA)$", message = "Para o tipo somente são aceitos ENTRADA ou SAÍDA")
    private String type;

    @NotNull(message = "Informe uma descrição")
    @Length(min = 5, message = "A descrição deve ter no mínimo 5 caracteres")
    private String description;

    @NotNull(message = "Informe um valor")
    private BigDecimal amount;
}
