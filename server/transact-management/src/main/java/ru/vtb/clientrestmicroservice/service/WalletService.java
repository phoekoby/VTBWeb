package ru.vtb.clientrestmicroservice.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.vtb.clientrestmicroservice.dto.UserDto;
import ru.vtb.clientrestmicroservice.dto.output.OutputWalletDto;
import ru.vtb.clientrestmicroservice.dto.output.OutputWalletHistory;

import java.util.List;

@Service
public interface WalletService {
    List<OutputWalletDto> getWallets(UserDto userDto);
    Long createNewWallet(UserDto userDto);
    OutputWalletDto getBalance(Long walletId);
    OutputWalletHistory getHistory(Long walletId, Pageable pageable);
}
