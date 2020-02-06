package orders.api;

import lombok.extern.slf4j.Slf4j;
import orders.data.ComponentRepositoryInterface;
import orders.data.OrderRepositoryInteface;
import orders.domain.Component;
import orders.domain.Order;
import orders.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path="/api2", produces={"application/json", "text/xml"})
@CrossOrigin(origins="*")

public class OrderRestConrtoller {
    private  ComponentRepositoryInterface componentRepo;
    @Autowired
    OrderRepositoryInteface orderRepo;

    @Autowired
    public OrderRestConrtoller(ComponentRepositoryInterface componentsRepo) {
        this.componentRepo = componentsRepo;
    }

    @GetMapping("/recent")
    public Iterable<Order> recentOrders() {
        PageRequest page = PageRequest.of(0,3, Sort.by("createdAt").descending());
        return orderRepo.findAll(page).getContent();
    }

    @GetMapping("/components")
    public Iterable<Component> allComponents() {
              return componentRepo.findAll();
    }

    @GetMapping("/components/{id}")
    public Component allComponents(@PathVariable Long id) {
        Optional<Component> optionalComponent = componentRepo.findById(id);
        if (optionalComponent.isPresent()) return optionalComponent.get();
        else return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> orderById(@PathVariable("id") Long id) {
        Optional<Order> optOrder = orderRepo.findById(id);
        if (optOrder.isPresent()) return new ResponseEntity<>(optOrder.get(), HttpStatus.OK);
        else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping(path="/saveOrder", consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Order saveOrder(@RequestBody Order order) {
     //   log.info("received order: "+order);
        return orderRepo.save(order);
    }

    @PatchMapping(path="/{id}", consumes="application/json")
    public Order patchOrder(@PathVariable("id") Long id, @RequestBody Order patch) {
    Order order = orderRepo.findById(id).get();
    if (patch.getName()!=null) order.setName(patch.getName());
    if (patch.getOrderTable()!=null) order.setOrderTable(patch.getOrderTable());
    if (patch.getPaymentType()!=null) order.setPaymentType(patch.getPaymentType());
    if (patch.getComponents()!=null) order.setComponents(patch.getComponents());

    return orderRepo.save(order);
    }

    @PutMapping (path="/{id}", consumes = "application/json")
    public Order putOrder (@PathVariable("id") Long id, @RequestBody Order putOrder) {
       return orderRepo.save(putOrder);
    }

    @DeleteMapping ("/{id}")
    @ResponseStatus(code=HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("id") Long id) {
        try{
            orderRepo.deleteById(id);
        }
        catch (EmptyResultDataAccessException ex) {}

    }


}
