package ru.vtb.clientrestmicroservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.clientrestmicroservice.entity.UserAccount;
import ru.vtb.clientrestmicroservice.repository.UserAccountRepository;
import ru.vtb.clientrestmicroservice.service.UserAccountService;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {
    private final UserAccountRepository userAccountRepository;
    @Override
    public UserAccount getUserAccount(Long userId) {
        Optional<UserAccount> byUserId = userAccountRepository.findByUserId(userId);
        UserAccount userAccount;
        if(byUserId.isEmpty()){
            userAccount = new UserAccount();
            userAccount.setUserId(userId);
            userAccount = userAccountRepository.save(userAccount);
        }else{
            userAccount = byUserId.get();
        }
        return userAccount;
    }
}
