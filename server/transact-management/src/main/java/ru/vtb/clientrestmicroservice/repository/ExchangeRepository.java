package ru.vtb.clientrestmicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vtb.clientrestmicroservice.entity.transaction.Exchange;

public interface ExchangeRepository extends JpaRepository<Exchange, Long> {
}
