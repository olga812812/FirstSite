package orders;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    @Size(min=1, message="You must input name")
    private  String name;

    @OneToMany (targetEntity = Component.class)
    @NotNull(message="You must choose at least 1 order component")
    private List<Component> components = new ArrayList<>();

    @Size(min=1, message="You must input table")
    private  String orderTable;

    @Size(min=1, message="You must input payment type")
    private  String paymentType;
    private  Date createdAt;

    @PrePersist
    void createdAt(){
        this.setCreatedAt(Date.valueOf(LocalDate.now()));
    }

}
