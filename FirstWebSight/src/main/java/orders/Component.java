package orders;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Date;

@Data
@RequiredArgsConstructor
public class Component {

    private final Long id;
    private final String name;
    private final Type type;
 //   private Date createdAt;

    public static enum Type {
        DRINKS, SNACK, FRUIT
    }


}
