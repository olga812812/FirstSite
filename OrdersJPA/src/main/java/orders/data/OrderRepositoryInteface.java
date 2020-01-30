package orders.data;

import orders.domain.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;

public interface OrderRepositoryInteface extends PagingAndSortingRepository<Order, Long> {

}
