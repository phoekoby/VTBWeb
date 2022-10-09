package ru.vtb.serverrpcmicroservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vtb.serverrpcmicroservice.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
