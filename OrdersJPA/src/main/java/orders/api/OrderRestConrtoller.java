package orders.api;

import orders.data.OrderRepositoryInteface;
import orders.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path="/api", produces={"application/json", "text/xml"})
@CrossOrigin(origins="*")

public class OrderRestConrtoller {
    @Autowired
    OrderRepositoryInteface orderRepo;

    @GetMapping("/recent")
    public Iterable<Order> recentOrders() {
        PageRequest page = PageRequest.of(0,3, Sort.by("createdAt").descending());
        return orderRepo.findAll(page).getContent();
    }

    @GetMapping("/{id}")
    public Order orderById(@PathVariable("id") Long id) {
        Optional<Order> optOrder = orderRepo.findById(id);
        if (optOrder.isPresent()) return optOrder.get();
        else return null;
    }

}
