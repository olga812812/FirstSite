package orders.data;

import orders.domain.Component;
import org.springframework.data.repository.CrudRepository;

public interface ComponentRepositoryInterface extends CrudRepository<Component, Long> {
}
