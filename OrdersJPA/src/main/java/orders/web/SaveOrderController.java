package orders.web;

import lombok.extern.slf4j.Slf4j;
import orders.data.OrderRepositoryInteface;
import orders.data.UserRepositoryInterface;
import orders.domain.Order;
import orders.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@Slf4j
@RequestMapping("/orders")
@SessionAttributes("order")

public class SaveOrderController {

    private UserRepositoryInterface userRepo;
    private OrderRepositoryInteface orderRepo;

    public SaveOrderController (UserRepositoryInterface userRepo, OrderRepositoryInteface orderRepo) {
        this.userRepo=userRepo;
        this.orderRepo=orderRepo;
    }

    @GetMapping("/current")
    public String saveOrderForm(Model model, @SessionAttribute("order") Order order) {
         model.addAttribute("order", order);
         return "saveOrderForm";
    }

    @PostMapping
    public String saveOrder(@Valid Order order, Errors errors, @AuthenticationPrincipal User user){
        if (errors.hasFieldErrors("ordertable") || errors.hasFieldErrors("paymentType")) return "saveOrderForm";
        log.info("Order ready for submitting: " + order);
        order.setUser(user);
        Order savedOrder = orderRepo.save(order);
        log.info("Order submitted: " + savedOrder);
        return "redirect:/orderInfo";
    }
}
