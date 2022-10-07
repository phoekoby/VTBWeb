package ru.vtb.clientrestmicroservice.service;

import org.springframework.stereotype.Service;
import ru.vtb.clientrestmicroservice.dto.UserDto;
import ru.vtb.clientrestmicroservice.dto.output.OutputWalletDto;
import ru.vtb.clientrestmicroservice.entity.Wallet;

import java.util.List;

@Service
public interface UserService {
    List<OutputWalletDto> getWallets(UserDto userDto);
}
