package orders;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;

interface OrderRepositoryInteface{
    RowMapper<Order> ORDER_ROW_MAPPER = (ResultSet rs, int rowNumber)-> {
        Order order = new Order();
        order.setId(rs.getLong(1));
        order.setName(rs.getString("name"));
        order.setTable(rs.getString("order_table"));
        order.setPaymentType(rs.getString("payment_type"));
        order.setCreatedAt(rs.getDate("created_at"));
        return order;};
    public Order findById(String id);
    public Order save(Order order);
}
