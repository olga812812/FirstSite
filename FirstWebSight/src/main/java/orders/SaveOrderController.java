package orders;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@Slf4j
@RequestMapping("/orders")
@SessionAttributes("order")

public class SaveOrderController {
    private OrderRepositorySimpleJdbcInsert orderRepo;
    @Autowired
    public SaveOrderController(OrderRepositorySimpleJdbcInsert orderRepo) {
        this.orderRepo = orderRepo;
    }

    @GetMapping("/current")
    public String saveOrderForm(Model model, @SessionAttribute("order") Order order) {
         model.addAttribute("order", order);
         return "saveOrderForm";
    }

    @PostMapping
    public String saveOrder(@Valid Order order, Errors errors){
        if (errors.hasFieldErrors("table") || errors.hasFieldErrors("paymentType")) return "saveOrderForm";
        log.info("Order ready for submitting: " + order);
        Order savedOrder = orderRepo.save(order);
        log.info("Order submitted: " + savedOrder);
        return "redirect:/";
    }
}
