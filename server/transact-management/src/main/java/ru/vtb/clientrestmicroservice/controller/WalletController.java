package ru.vtb.clientrestmicroservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.vtb.clientrestmicroservice.dto.UserDto;
import ru.vtb.clientrestmicroservice.dto.output.OutputWalletDto;
import ru.vtb.clientrestmicroservice.dto.output.OutputWalletHistory;
import ru.vtb.clientrestmicroservice.service.WalletService;

import java.util.List;

@Controller
@RequestMapping("/wallet")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @GetMapping
    public ResponseEntity<List<OutputWalletDto>> getMyWallets(){
        UserDto userDto = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(walletService.getWallets(userDto.getId()));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<OutputWalletDto>> getWalletsByUser(@PathVariable Long userId){
        return ResponseEntity.ok(walletService.getWallets(userId));
    }

    @PostMapping
    public ResponseEntity<Long> createNew(){
        UserDto userDto = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(walletService.createNewWallet(userDto));
    }


    @GetMapping("/{id}/history")
    public ResponseEntity<OutputWalletHistory> getHistory(@PathVariable Long id, Pageable page){
        return ResponseEntity.ok(walletService.getHistory(id, page));
    }
}
