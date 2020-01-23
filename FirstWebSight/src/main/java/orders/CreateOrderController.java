package orders;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


import orders.Component.Type;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping ("/createOrder")
@SessionAttributes("order")

public class CreateOrderController {
    private List<Component> components;
    private Map<String,?> map;
    private final ComponentRepository componentRepo;


    @Autowired
    public CreateOrderController(ComponentRepository componentsRepo) {
        this.componentRepo = componentsRepo;
    }
    @ModelAttribute(name="order")
    public Order order(){
        return new Order();
    }

    @GetMapping
    public String showCreateOrderForm(Model model) {
        getOrderCompopents();
        addComponentTypesToModel(model);
       // model.addAttribute("order", new Order());
        map = model.asMap();
        log.info("Processing map: " + map);
        return "createOrder";
    }

    private void getOrderCompopents() {
            components = new ArrayList<>();
            componentRepo.findAll().forEach(component->components.add(component));
           }

    private void addComponentTypesToModel(Model model) {
        Type[] types  = Type.values();
        for (Type type: types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(components, type));
        }
    }

    private List<Component> filterByType(List<Component> list, Type type) {
        return list.stream().filter(component->component.getType().equals(type)).collect(Collectors.toList());
    }

    @PostMapping
    public String processOrder(@Valid Order order, Errors errors, Model model) {
        model.mergeAttributes(map);
        log.info("Processing order: " + order);
        if (errors.hasFieldErrors("name") || errors.hasFieldErrors("components")) return "createOrder";
        log.info("Processing order: " + order);
        return "redirect:/orders/current";
    }
}
