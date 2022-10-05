package ru.vtb.integrationmodule.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vtb.integrationmodule.entity.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
