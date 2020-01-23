package orders;

import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;

interface OrderRepositoryInteface extends CrudRepository <Order, Long> {

}
