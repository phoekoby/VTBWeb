package ru.vtb.clientrestmicroservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.vtb.clientrestmicroservice.entity.transaction.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {


    @Query("select t from Transaction t " +
            "join Wallet fr on t.fromWallet = fr " +
            "join Wallet to on t.toWallet = to " +
            "where fr.id = :walletId or to.id = :walletId")
    Page<Transaction> getAllByWalletId(Pageable pageable, Long walletId);
}
