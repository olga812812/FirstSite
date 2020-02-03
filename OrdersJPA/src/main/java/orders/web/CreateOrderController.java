package orders.web;

import lombok.extern.slf4j.Slf4j;
import orders.data.ComponentRepositoryInterface;
import orders.domain.Component;
import orders.domain.Component.Type;
import orders.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping ("/createOrder")
@SessionAttributes("order")

public class CreateOrderController {
    private List<Component> components;
    private final ComponentRepositoryInterface componentRepo;

    @Autowired
    public CreateOrderController(ComponentRepositoryInterface componentsRepo) {
        this.componentRepo = componentsRepo;
    }

    @GetMapping
    public String showCreateOrderForm(Model model) {
        model.addAttribute("order", new Order());
        getOrderCompopents();
        addComponentTypesToModel(model);
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
        log.info("Processing order: " + order);
        if (errors.hasFieldErrors("name") || errors.hasFieldErrors("components")) return "createOrder";
        log.info("Processing order: " + order);
        return "redirect:/orders/current";
    }
}
