package orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Repository
public class OrderRepositorySimpleJdbcInsert implements OrderRepositoryInteface {
        private SimpleJdbcInsert orderInserter;
        private SimpleJdbcInsert orderComponentsInserter;
        private JdbcTemplate jdbc;

        @Autowired

    public OrderRepositorySimpleJdbcInsert(JdbcTemplate jdbc) {
            this.jdbc = jdbc;
            this.orderInserter = new SimpleJdbcInsert(jdbc).withTableName("Orders").usingGeneratedKeyColumns("id");
            this.orderComponentsInserter = new SimpleJdbcInsert(jdbc).withTableName("order_components");
        }

    public Order findById (String id) {
        return jdbc.queryForObject("select * from orders where id=?", ORDER_ROW_MAPPER, id);
    }

    public Order save(Order order) {
        order.setCreatedAt(Date.valueOf(LocalDate.now()));
        long orderId = saveOrderInfo(order);
        order.setId(orderId);
        for (Component component: order.getComponents()) {
            saveComponentToOrder(component, orderId);
        }
        return order;
    }

    private long saveOrderInfo(Order order) {
            return orderInserter.executeAndReturnKey(createMapWithOrderValues(order)).longValue();
        }

    private Map<String, Object> createMapWithOrderValues(Order order){
        Map<String, Object> orderValues = new HashMap<>();
        orderValues.put("name", order.getName());
        orderValues.put("order_table", order.getTable());
        orderValues.put("payment_type", order.getPaymentType());
        orderValues.put("created_at", order.getCreatedAt());
        return orderValues;
    }

    private void saveComponentToOrder(Component component, long orderId) {
            Map<String, Object> orderComponent = new HashMap<>();
            orderComponent.put("order_id", orderId);
            orderComponent.put("component_id", component.getId());
            orderComponentsInserter.execute(orderComponent);

    }
}
