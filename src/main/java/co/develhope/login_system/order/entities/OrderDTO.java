package co.develhope.login_system.order.entities;

import lombok.Data;

@Data
public class OrderDTO {
    private String description;
    private String address;
    private int number;
    private String city;
    private int zipCode;
    private String state;
    private Long restaurant;
    private double price;
}