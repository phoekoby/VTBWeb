package ru.vtb.clientrestmicroservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.vtb.clientrestmicroservice.dto.UserDto;
import ru.vtb.clientrestmicroservice.dto.output.OutputWalletDto;
import ru.vtb.clientrestmicroservice.service.WalletService;

import java.util.List;

@Controller
@RequestMapping("/wallet")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;


    @GetMapping
    public List<OutputWalletDto> getWallets(){
        UserDto userDto = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return walletService.getWallets(userDto);
    }

    @PostMapping
    public Long createNew(){
        UserDto userDto = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return walletService.createNewWallet(userDto);
    }



}
