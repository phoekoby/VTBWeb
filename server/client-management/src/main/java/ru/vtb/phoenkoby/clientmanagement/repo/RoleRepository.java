package ru.vtb.phoenkoby.clientmanagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vtb.phoenkoby.clientmanagement.domain.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
//    Role getRoleByName(String name);
    Optional<Role> getRoleByName(String name);
}
