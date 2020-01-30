package orders.data;

import orders.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface UserRepositoryInterface extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
