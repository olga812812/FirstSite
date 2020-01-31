package orders;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;


@Repository
public class OrderRepositoryJDBCTemplate implements OrderRepositoryInteface {

    private JdbcTemplate jdbc;
    @Autowired
    public OrderRepositoryJDBCTemplate(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    public Order findById (String id) {
        return jdbc.queryForObject("select * from orders where id=?", ORDER_ROW_MAPPER, id);
    }

    public Order save(Order order) {
        long orderId = saveOrderInfo(order);
        order.setId(orderId);
        for (Component component: order.getComponents()) {
            saveComponentsToOrder(component, orderId);
        }
        return order;
    }

    private long saveOrderInfo(Order order) {
        order.setCreatedAt(Date.valueOf(LocalDate.now()));
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                "insert into orders (name, order_table, payment_type, created_at) values (?, ?, ?, ?)",
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP);
        pscf.setReturnGeneratedKeys(true);
        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(
                Arrays.asList(order.getName(),
                              order.getTable(),
                              order.getPaymentType(),
                              new Timestamp(order.getCreatedAt().getTime())));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(psc, keyHolder);
        return keyHolder.getKey().longValue();

            }

    private void saveComponentsToOrder(Component component, long orderId) {
        jdbc.update("insert into order_components (order_id, component_id) values (?, ?)", orderId, component.getId());

    }
}
