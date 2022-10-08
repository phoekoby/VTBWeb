package ru.vtb.clientrestmicroservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<List<OutputWalletDto>> getWallets(){
        UserDto userDto = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(walletService.getWallets(userDto));
    }

    @PostMapping
    public ResponseEntity<Long> createNew(){
        UserDto userDto = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(walletService.createNewWallet(userDto));
    }


    @GetMapping("/{id}/history")
    public ResponseEntity<OutputWalletHistory> getHistory(@PathVariable Long id,
                                          @RequestParam(required = false, defaultValue = "1L") Long page,
                                          @RequestParam(required = false, defaultValue = "1L") Long offset,
                                          @RequestParam(required = false, defaultValue = "asc") String sort){
        return ResponseEntity.ok(walletService.getHistory(id, page, offset, sort));

    }
}
