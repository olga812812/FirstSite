package orders;

import orders.ComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;


@org.springframework.stereotype.Component

public class ComponentByIdConverter implements Converter<String, Component> {
    private ComponentRepository componentRepo;

    @Autowired
    public ComponentByIdConverter(ComponentRepository componentRepo) {
        this.componentRepo=componentRepo;
    }

    @Override
    public orders.Component convert(String id) {
        return componentRepo.findById(id);
    }



}
