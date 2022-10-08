package ru.vtb.clientrestmicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.vtb.clientrestmicroservice.entity.transaction.Wallet;

import java.util.List;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    @Query("select w from Wallet w join UserAccount u on w.userAccount = u where u.userId = :userId")
    List<Wallet> getWalletByUserId(Long userId);
}
