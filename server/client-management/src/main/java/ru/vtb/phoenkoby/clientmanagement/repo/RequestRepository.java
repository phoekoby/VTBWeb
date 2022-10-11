package ru.vtb.phoenkoby.clientmanagement.repo;

import ru.vtb.phoenkoby.clientmanagement.domain.Request;
import ru.vtb.phoenkoby.clientmanagement.domain.enumiration.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vtb.phoenkoby.clientmanagement.domain.User;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByToAndRequestStatus(User user, RequestStatus requestStatus);

    List<Request> findAllByToOrFrom(User to, User from);

}
