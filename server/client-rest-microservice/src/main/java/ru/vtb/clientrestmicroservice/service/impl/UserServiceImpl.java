package ru.vtb.clientrestmicroservice.service.impl;

import ru.vtb.clientrestmicroservice.dto.UserDto;
import ru.vtb.clientrestmicroservice.dto.output.OutputWalletDto;
import ru.vtb.clientrestmicroservice.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    @Override
    public List<OutputWalletDto> getWallets(UserDto userDto) {
        return null;
    }
}
