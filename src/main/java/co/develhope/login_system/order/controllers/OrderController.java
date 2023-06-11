package co.develhope.login_system.order.controllers;

import co.develhope.login_system.order.entities.Order;
import co.develhope.login_system.order.entities.OrderDTO;
import co.develhope.login_system.order.repositories.OrderRepository;
import co.develhope.login_system.order.services.OrderService;
import co.develhope.login_system.user.entities.User;
import co.develhope.login_system.user.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
//@PreAuthorize("hasRole('"+Roles.REGISTERED+"')")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderService orderService;

    @PostMapping
    @PostAuthorize("hasRole('"+ Roles.REGISTERED+"')")
    public ResponseEntity<Order> createOrder(@RequestBody OrderDTO order) throws Exception {
        return ResponseEntity.ok(orderService.savaOrder(order));
    }
    @GetMapping("/{id}")
//  @PostAuthorize("hasRole('"+Roles.REGISTERED+"') OR returnObject.body == null OR returnObject.body.createdBy.id == authentication.principal.id")
    public ResponseEntity<Order> getOrder(@PathVariable Long id){
        Optional <Order> order = orderRepository.findById(id);
        if(!order.isPresent()) return ResponseEntity.notFound().build();
        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        if(Roles.hasRole(user,Roles.REGISTERED)
                && order.get().getCreatedBy().getId() == user.getId()){
            return ResponseEntity.ok(order.get());
        } else if (Roles.hasRole(user,Roles.RESTAURANT)
                && order.get().getRestaurant().getId() == user.getId()) {
            return ResponseEntity.ok(order.get());
        } else if (Roles.hasRole(user,Roles.RIDER)
                && order.get().getRider().getId() == user.getId()) {
            return ResponseEntity.ok(order.get());
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders(){
        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    if(Roles.hasRole(user,Roles.REGISTERED)){
        return ResponseEntity.ok(orderRepository.findByCreatedBy(user));
    } else if (Roles.hasRole(user, Roles.RESTAURANT)) {
        return ResponseEntity.ok(orderRepository.findByRestaurant(user));
    } else if (Roles.hasRole(user, Roles.RIDER)) {
        return ResponseEntity.ok(orderRepository.findByRider(user));
    }
    return null;
    }

}
/*               ====== ESEMPI DI GESTIONE DEI RUOLI ======
@RestController
@RequestMapping("/orders")
@PreAuthorize("hasRole('"+Roles.REGISTERED+"')")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order){
        return ResponseEntity.ok(orderService.savaOrder(order));
    }

    @GetMapping("/{id}")
**  @PostAuthorize("hasRole('"+Roles.REGISTERED+"') OR returnObject.body == null OR returnObject.body.createdBy.id == authentication.principal.id")
    public ResponseEntity<Order> getOrder(@PathVariable Long id){
        Optional <Order> order = orderRepository.findById(id);
        if(!order.isPresent()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(order.get());
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders(){
        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        boolean isAdmin = user.getRoles().stream().anyMatch(role -> role.getName().equals(Roles.ADMIN));
        if(isAdmin){
            return ResponseEntity.ok(orderRepository.findAll());
        }else return ResponseEntity.ok(orderRepository.findByCreatedBy(user));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id){
        if(!orderService.canEdit(id)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(orderService.updateOrder(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrder(@PathVariable Long id){
        if(!orderService.canEdit(id)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        orderRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
 */