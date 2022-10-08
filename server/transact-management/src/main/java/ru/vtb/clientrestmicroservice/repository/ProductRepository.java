package ru.vtb.clientrestmicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vtb.clientrestmicroservice.entity.transaction.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
