package ru.vtb.clientrestmicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vtb.clientrestmicroservice.entity.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
