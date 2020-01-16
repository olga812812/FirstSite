package orders;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Component {

    private final String id;
    private final String name;
    private final Type type;

    public static enum Type {
        DRINKS, SNACK, FRUIT
    }


}
