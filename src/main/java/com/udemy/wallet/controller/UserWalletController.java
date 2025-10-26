package com.udemy.wallet.controller;

import com.udemy.wallet.dto.UserWalletDTO;
import com.udemy.wallet.entity.User;
import com.udemy.wallet.entity.UserWallet;
import com.udemy.wallet.entity.Wallet;
import com.udemy.wallet.impl.UserWalletServiceImpl;
import com.udemy.wallet.response.Response;
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
@RequestMapping("user-wallet")
public class UserWalletController {

    @Autowired
    private UserWalletServiceImpl userWalletService;

    @PostMapping
    public ResponseEntity<Response<UserWalletDTO>> create(@Valid @RequestBody UserWalletDTO dto, BindingResult result) {

        Response<UserWalletDTO> response = new Response<>();

        if(result.hasErrors()) {
            result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        UserWallet userWallet = userWalletService.save(convertDtoToEntity(dto));

        response.setData(convertEntityToDto(userWallet));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    private UserWallet convertDtoToEntity(UserWalletDTO dto) {
        UserWallet userWallet = new UserWallet();

        User user = new User();
        user.setId(dto.getUsers());

        Wallet wallet = new Wallet();
        wallet.setId(dto.getWallet());

        userWallet.setId(dto.getId());
        userWallet.setWallet(wallet);
        userWallet.setUsers(user);

        return userWallet;
    }

    private UserWalletDTO convertEntityToDto(UserWallet userWallet) {
        UserWalletDTO dto = new UserWalletDTO();
        dto.setId(userWallet.getId());
        dto.setWallet(userWallet.getWallet().getId());
        dto.setUsers(userWallet.getUsers().getId());

        return dto;
    }

}
