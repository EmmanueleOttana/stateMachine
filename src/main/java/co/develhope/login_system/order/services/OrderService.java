package co.develhope.login_system.order.services;

import co.develhope.login_system.order.entities.Order;
import co.develhope.login_system.order.entities.OrderDTO;
import co.develhope.login_system.order.entities.OrderStatus;
import co.develhope.login_system.order.repositories.OrderRepository;
import co.develhope.login_system.user.entities.User;
import co.develhope.login_system.user.repositories.UserRepository;
import co.develhope.login_system.user.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    public Order savaOrder(OrderDTO orderInput) throws Exception {
        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());
        order.setCreatedBy(user);
        order.setAddress(orderInput.getAddress());
        order.setCity(orderInput.getCity());
        order.setDescription(orderInput.getDescription());
        order.setState(orderInput.getState());
        order.setNumber(orderInput.getNumber());
        order.setPrice(orderInput.getPrice());
        order.setZipCode(orderInput.getZipCode());
        if(orderInput.getRestaurant() == null) throw new Exception("Restaurant is null!");
        Optional<User> restaurant = userRepository.findById(orderInput.getRestaurant());
        if(restaurant.isEmpty() || !Roles.hasRole(restaurant.get(), Roles.RESTAURANT)) throw new Exception("Restaurant not found!");
        order.setRestaurant(restaurant.get());
        order.setStatus(OrderStatus.CREATED);
        return orderRepository.saveAndFlush(order);
    }
    public Order updateOrder(Long id){
        Order orderInput = new Order();
        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        orderInput.setId(id);
        orderInput.setUpdateAt(LocalDateTime.now());
        orderInput.setUpdateBy(user);
        return orderRepository.saveAndFlush(orderInput);
    }

    public boolean canEdit(Long id) {
        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Optional <Order> order = orderRepository.findById(id);
        if (!order.isPresent()) return false;
        return order.get().getCreatedBy().getId() == user.getId();
    }
}
