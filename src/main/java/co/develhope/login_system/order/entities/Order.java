package co.develhope.login_system.order.entities;

import co.develhope.login_system.user.entities.User;
import co.develhope.login_system.utils.entities.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String description;
    private String address;
    private int number;
    private String city;
    private int zipCode;
    private String state;
    private OrderStatus status;
    @ManyToOne
    private User restaurant;
    @ManyToOne
    private User rider;
    private double price;


}