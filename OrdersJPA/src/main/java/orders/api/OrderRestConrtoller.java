package orders.api;

import orders.data.OrderRepositoryInteface;
import orders.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api", produces="application/json")
@CrossOrigin(origins="*")

public class OrderRestConrtoller {
    @Autowired
    OrderRepositoryInteface orderRepo;

    @GetMapping("/recent")
    public Iterable<Order> recentOrders() {
        PageRequest page = PageRequest.of(0,3, Sort.by("createdAt").descending());
        return orderRepo.findAll(page).getContent();
    }
}
