package orders;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.List;

@Data
public class Order {
    private  Long id;
    @Size(min=1, message="You must input name")
    private  String name;
    @NotNull(message="You must choose at least 1 order component")
    private List<Component> components;
    @Size(min=1, message="You must input table")
    private  String table;
    @Size(min=1, message="You must input payment type")
    private  String paymentType;
    private  Date createdAt;

}
