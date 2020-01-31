package orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

interface ComponentRepositoryInterface {
    RowMapper<Component> COMPONENT_ROW_MAPPER = (ResultSet rs, int rowNumber) -> new Component(
            rs.getLong("id"),
            rs.getString("name"),
            Component.Type.valueOf(rs.getString("type")));
    public Iterable<Component> findAll();
    public Component findById(String id);

}

@Repository
public class ComponentRepository implements ComponentRepositoryInterface {
    private JdbcTemplate jdbc;
    @Autowired
    public ComponentRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        }

    public Iterable<Component> findAll() {
        return jdbc.query("select id, name, type from Components", COMPONENT_ROW_MAPPER);
    }

    public Component findById (String id) {
        return jdbc.queryForObject("select * from Components where id=?", COMPONENT_ROW_MAPPER, id);

    }



}
