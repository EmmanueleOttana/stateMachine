package co.develhope.login_system.order.services;

import co.develhope.login_system.order.entities.Order;
import co.develhope.login_system.order.entities.OrderStatus;
import co.develhope.login_system.order.repositories.OrderRepository;
import co.develhope.login_system.user.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderStateService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private RiderService riderService;

    public Order setAccept(Order order)throws Exception {
        if(order == null) throw new NullPointerException();
        if(order.getStatus() != OrderStatus.CREATED) throw new Exception("Cannot edit order!");
        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        if(order.getRestaurant().getId() != user.getId())throw new Exception("This is not your order!");
        order.setStatus(OrderStatus.ACCEPTED);
        order.setUpdateAt(LocalDateTime.now());
        order.setUpdateBy(user);
        return orderRepository.saveAndFlush(order);
    }

    public Order setInPreparation(Order order)throws Exception {
        if(order == null) throw new NullPointerException();
        if(order.getStatus() != OrderStatus.ACCEPTED) throw new Exception("Cannot edit order!");
        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        if(order.getRestaurant().getId() != user.getId())throw new Exception("This is not your order!");
        order.setStatus(OrderStatus.IN_PREPARATION);
        order.setUpdateAt(LocalDateTime.now());
        order.setUpdateBy(user);
        return orderRepository.saveAndFlush(order);
    }

    public Order setReady(Order order)throws Exception {
        if(order == null) throw new NullPointerException();
        if(order.getStatus() != OrderStatus.IN_PREPARATION) throw new Exception("Cannot edit order!");
        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        if(order.getRestaurant().getId() != user.getId())throw new Exception("This is not your order!");
        User rider = riderService.pickRider();
        order.setRider(rider);
        order.setStatus(OrderStatus.READY);
        order.setUpdateAt(LocalDateTime.now());
        order.setUpdateBy(user);
        return orderRepository.saveAndFlush(order);
    }

    public Order setDelivering(Order order)throws Exception {
        if(order == null) throw new NullPointerException();
        if(order.getStatus() != OrderStatus.READY) throw new Exception("Cannot edit order!");
        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        if(order.getRider().getId() != user.getId())throw new Exception("This is not your order!");
        order.setStatus(OrderStatus.DELIVERING);
        order.setUpdateAt(LocalDateTime.now());
        order.setUpdateBy(user);
        return orderRepository.saveAndFlush(order);
    }

    public Order setComplete(Order order)throws Exception{
        if(order == null) throw new NullPointerException();
        if(order.getStatus() != OrderStatus.DELIVERING) throw new Exception("Cannot edit order!");
        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        if(order.getRider().getId() != user.getId())throw new Exception("This is not your order!");
        order.setStatus(OrderStatus.COMPLETED);
        order.setUpdateAt(LocalDateTime.now());
        order.setUpdateBy(user);
        return orderRepository.saveAndFlush(order);
    }
}
