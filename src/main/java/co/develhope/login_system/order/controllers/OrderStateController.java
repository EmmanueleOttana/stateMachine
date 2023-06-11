package co.develhope.login_system.order.controllers;

import co.develhope.login_system.order.entities.Order;
import co.develhope.login_system.order.repositories.OrderRepository;
import co.develhope.login_system.order.services.OrderStateService;
import co.develhope.login_system.user.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/order/{id}/state")
public class OrderStateController {

    @Autowired
    private OrderStateService orderStateService;
    @Autowired
    private OrderRepository orderRepository;

    @PreAuthorize("hasRole('ROLE_"+Roles.RESTAURANT+"')")
    @PutMapping("/accepted")
    public ResponseEntity<Order> accepted(@PathVariable long id) throws Exception {
        Optional<Order> order = orderRepository.findById(id);
        if(order.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orderStateService.setAccept(order.get()));
    }
    @PreAuthorize("hasRole('ROLE_"+Roles.RESTAURANT+"')")
    @PutMapping("/preparation")
    public ResponseEntity<Order> inPreparation(@PathVariable long id)throws Exception{
        Optional<Order> order = orderRepository.findById(id);
        if(order.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orderStateService.setInPreparation(order.get()));
    }
    @PreAuthorize("hasRole('ROLE_"+Roles.RESTAURANT+"')")
    @PutMapping("/ready")
    public ResponseEntity<Order> ready(@PathVariable long id)throws Exception{
        Optional<Order> order = orderRepository.findById(id);
        if(order.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orderStateService.setReady(order.get()));
    }
    @PreAuthorize("hasRole('ROLE_"+Roles.RIDER+"')")
    @PutMapping("/delivering")
    public ResponseEntity<Order> delivering(@PathVariable long id)throws Exception{
        Optional<Order> order = orderRepository.findById(id);
        if(order.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orderStateService.setDelivering(order.get()));
    }
    @PreAuthorize("hasRole('ROLE_"+Roles.RIDER+"')")
    @PutMapping("/complete")
    public ResponseEntity<Order> complete(@PathVariable long id)throws Exception{
        Optional<Order> order = orderRepository.findById(id);
        if(order.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orderStateService.setComplete(order.get()));
    }

}
