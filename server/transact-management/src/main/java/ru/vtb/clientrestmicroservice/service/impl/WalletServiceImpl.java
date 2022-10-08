package ru.vtb.clientrestmicroservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.vtb.clientrestmicroservice.dto.UserDto;
import ru.vtb.clientrestmicroservice.dto.moneyApi.ApiBalanceDto;
import ru.vtb.clientrestmicroservice.dto.moneyApi.ApiNftBalanceResponseDto;
import ru.vtb.clientrestmicroservice.dto.moneyApi.ApiWalletDto;
import ru.vtb.clientrestmicroservice.dto.output.OutNftDto;
import ru.vtb.clientrestmicroservice.dto.output.OutputWalletDto;
import ru.vtb.clientrestmicroservice.dto.output.OutputWalletHistory;
import ru.vtb.clientrestmicroservice.entity.Wallet;
import ru.vtb.clientrestmicroservice.repository.WalletRepository;
import ru.vtb.clientrestmicroservice.service.TransactionService;
import ru.vtb.clientrestmicroservice.service.UserAccountService;
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
    private final UserAccountService userAccountService;
    private final TransactionService transactionService;

    @Override
    public List<OutputWalletDto> getWallets(Long userId) {
        return walletRepository.getWalletByUserId(userId).stream()
                .map(Wallet::getId)
                .map(this::getBalance).collect(Collectors.toList());
    }

    @Override
    public Long createNewWallet(UserDto userDto) {
        try {
            ApiWalletDto apiWalletDto = restTemplate.postForObject(baseUrl + "wallets/new", null, ApiWalletDto.class);
            Wallet wallet = new Wallet();
            if(apiWalletDto != null) {
                wallet.setPrivateKey(apiWalletDto.getPrivateKey());
                wallet.setPublicKey(apiWalletDto.getPublicKey());
                wallet.setUserAccount(userAccountService.getUserAccount(userDto.getId()));
                return walletRepository.save(wallet).getId();
            }else{
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Wallet can't be created");
            }
        }catch (RestClientException restClientException){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, " Error while request processing: \n" + restClientException.getLocalizedMessage());
        }
    }

    @Override
    public OutputWalletDto getBalance(Long walletId) {
        Optional<Wallet> walletOptional = walletRepository.findById(walletId);
        if(walletOptional.isPresent()){
            Wallet wallet = walletOptional.get();
            String publicKey = wallet.getPublicKey();
            String url = baseUrl + "wallets/" + publicKey + "/balance";
            String nftUrl = baseUrl + "wallets/" + publicKey + "/nft/balance";
            try {
                ResponseEntity<ApiBalanceDto> forEntity = restTemplate.getForEntity(url, ApiBalanceDto.class);
                ResponseEntity<ApiNftBalanceResponseDto> nftEntity = restTemplate.getForEntity(nftUrl, ApiNftBalanceResponseDto.class);
                ApiBalanceDto body = forEntity.getBody();
                ApiNftBalanceResponseDto apiNftBalanceResponseDto = nftEntity.getBody();
                if(body != null && apiNftBalanceResponseDto != null){
                    return OutputWalletDto.builder()
                            .id(walletId)
                            .matic(body.getMaticAmount())
                            .rubles(body.getCoinsAmount())
                            .nfts(apiNftBalanceResponseDto.getBalance().stream().map(apiNftBalanceDto -> OutNftDto.builder()
                                    .url(apiNftBalanceDto.getURI())
                                    .tokens(apiNftBalanceDto.getTokens())
                                    .build()).collect(Collectors.toList()))
                            .build();
                }else{
                    throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "wallet doesn't found");
                }
            }catch (RestClientException restClientException){
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, " Error while request processing: \n" + restClientException.getLocalizedMessage());
            }
        }else{
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "wallet doesn't found");
        }
    }

    @Override
    public OutputWalletHistory getHistory(Long walletId, Pageable pageable) {
        return OutputWalletHistory.builder()
                .walletId(walletId)
                .transactions(transactionService.getTransfersByWalletId(walletId, pageable))
                .build();
    }

}
