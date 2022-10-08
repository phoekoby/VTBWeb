package ru.vtb.phoekoby.client.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vtb.phoekoby.client.domain.Request;
import ru.vtb.phoekoby.client.domain.User;
import ru.vtb.phoekoby.client.domain.enumiration.RequestStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByToAndRequestStatus(User user, RequestStatus requestStatus);

    List<Request> findAllByToOrFrom(User to, User from);

}
