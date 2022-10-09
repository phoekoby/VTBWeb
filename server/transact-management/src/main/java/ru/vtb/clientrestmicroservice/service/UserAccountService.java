package ru.vtb.clientrestmicroservice.service;

import ru.vtb.clientrestmicroservice.entity.UserAccount;

public interface UserAccountService {
    UserAccount getUserAccount(Long userId);
    UserAccount createUserAccount(Long userId);
}
