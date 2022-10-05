package ru.vtb.integrationmodule.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vtb.integrationmodule.entity.Exchange;

public interface ExchangeRepository extends JpaRepository<Exchange, Long> {
}
