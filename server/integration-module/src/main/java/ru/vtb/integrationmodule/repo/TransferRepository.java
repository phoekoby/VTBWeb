package ru.vtb.integrationmodule.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vtb.integrationmodule.entity.Transfer;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
