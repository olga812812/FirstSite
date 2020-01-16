package orders;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import orders.Component.Type;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping ("/createOrder")
@SessionAttributes("order")

public class CreateOrderController {
    private List<Component> components;
    private Map<String,?> map;

    @GetMapping
    public String showCreateOrderForm(Model model) {
        getOrderCompopents();
        addComponentTypesToModel(model);
        model.addAttribute("order", new Order());
        map = model.asMap();
        return "createOrder";
    }

    private void getOrderCompopents() {
            components = Arrays.asList(
                new Component("1", "Apple", Type.FRUIT),
                new  Component("2", "Orange", Type.FRUIT),
                new  Component("3", "Cappucino", Type.DRINKS),
                new  Component("4", "Latte", Type.DRINKS),
                new  Component("5", "Chips", Type.SNACK),
                new  Component("6", "Burger", Type.SNACK)
        );

    }

    private void addComponentTypesToModel(Model model) {
        Type[] types  = Type.values();
        for (Type type: types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(components, type));
        }
    }

    private List<Component> filterByType(List<Component> list, Type type) {
        return list.stream().filter(x->x.getType().equals(type)).collect(Collectors.toList());
    }

    @PostMapping
    public String processOrder(@Valid Order order, Errors errors, Model model) {
        model.mergeAttributes(map);
        if (errors.hasFieldErrors("name") || errors.hasFieldErrors("ingredients")) return "createOrder";
        log.info("Processing order: " + order);
        return "redirect:/orders/current";
    }
}
