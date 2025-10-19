package com.udemy.wallet.controller;

import com.udemy.wallet.dto.WalletDTO;
import com.udemy.wallet.entity.Wallet;
import com.udemy.wallet.response.Response;
import com.udemy.wallet.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PostMapping
    public ResponseEntity<Response<WalletDTO>> create(@Valid @RequestBody WalletDTO dto, BindingResult result) {

        Response<WalletDTO> response = new Response<>();

        if(result.hasErrors()) {
            result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Wallet wallet = walletService.save(convertDtotoEntity(dto));

        response.setData(convertEntityToDto(wallet));

        return  ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    private Wallet convertDtotoEntity(WalletDTO dto) {
        Wallet wallet = new Wallet();
        wallet.setId(dto.getId());
        wallet.setName(dto.getName());
        wallet.setAmount(dto.getAmount());

        return wallet;
    }

    private WalletDTO convertEntityToDto(Wallet wallet) {
        WalletDTO dto = new WalletDTO();
        dto.setId(wallet.getId());
        dto.setName(wallet.getName());
        dto.setAmount(wallet.getAmount());

        return dto;
    }

}
