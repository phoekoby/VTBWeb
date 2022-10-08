package ru.vtb.serverrpcmicroservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vtb.serverrpcmicroservice.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
