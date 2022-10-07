package ru.vtb.clientrestmicroservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.vtb.clientrestmicroservice.dto.UserDto;
import ru.vtb.clientrestmicroservice.dto.moneyApi.ApiBalanceDto;
import ru.vtb.clientrestmicroservice.dto.moneyApi.ApiWalletDto;
import ru.vtb.clientrestmicroservice.dto.output.OutputWalletDto;
import ru.vtb.clientrestmicroservice.entity.Wallet;
import ru.vtb.clientrestmicroservice.repository.WalletRepository;
import ru.vtb.clientrestmicroservice.service.WalletService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final RestTemplate restTemplate;
    @Value("${base.url}")
    private String baseUrl;
    private final WalletRepository walletRepository;

    @Override
    public List<OutputWalletDto> getWallets(UserDto userDto) {
        return walletRepository.getWalletByUserId(userDto.getId()).stream()
                .map(Wallet::getId)
                .map(this::getBalance).collect(Collectors.toList());
    }

    @Override
    public Long createNewWallet(UserDto userDto) {
        ApiWalletDto apiWalletDto = restTemplate.postForObject(baseUrl + "wallets/new", null, ApiWalletDto.class);
        Wallet wallet = new Wallet();
        if(apiWalletDto != null) {
            wallet.setPrivateKey(apiWalletDto.getPrivateKey());
            wallet.setPublicKey(apiWalletDto.getPublicKey());
            wallet.setUserId(userDto.getId());
            return walletRepository.save(wallet).getId();
        }
        return null;
    }

    @Override
    public OutputWalletDto getBalance(Long walletId) {
        Optional<Wallet> walletOptional = walletRepository.findById(walletId);
        if(walletOptional.isPresent()){
            Wallet wallet = walletOptional.get();
            String publicKey = wallet.getPublicKey();
            String url = baseUrl + "wallets/" + publicKey + "/balance";
            ResponseEntity<ApiBalanceDto> forEntity = restTemplate.getForEntity(url, ApiBalanceDto.class);
            ApiBalanceDto body = forEntity.getBody();
            if(body != null){
                return OutputWalletDto.builder()
                        .id(walletId)
                        .matic(body.getMaticAmount())
                        .rubles(body.getCoinsAmount())
                        .build();
            }
        }
        return null;
    }

}
