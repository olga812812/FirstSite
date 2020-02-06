package orders.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@RequiredArgsConstructor
//@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
@Entity
@Table(name = "components")
public class Component {
    @Id
    private  Long id;
    private  String name;
    private  Type type;
 //   private Date createdAt;

    public static enum Type {
        DRINKS, SNACK, FRUIT
    }


}
