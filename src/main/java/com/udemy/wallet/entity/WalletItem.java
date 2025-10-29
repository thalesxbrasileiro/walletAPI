package com.udemy.wallet.entity;

import com.udemy.wallet.util.enums.TypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wallet_items")
public class WalletItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "wallet", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Wallet wallet;

    @NotNull
    private Date date;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TypeEnum type;

    @NotNull
    private String description;

    @NotNull
    private BigDecimal amount;
}
