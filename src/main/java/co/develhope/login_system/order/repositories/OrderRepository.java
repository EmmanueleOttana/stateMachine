package co.develhope.login_system.order.repositories;

import co.develhope.login_system.order.entities.Order;
import co.develhope.login_system.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByCreatedBy(User user);
    List<Order> findByRestaurant(User user);
    List<Order> findByRider(User user);
}
