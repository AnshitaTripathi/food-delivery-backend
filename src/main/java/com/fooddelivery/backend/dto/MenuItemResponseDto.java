package com.fooddelivery.backend.dto;

public class MenuItemResponseDto {

    private Long id;
    private String name;
    private double price;
    private boolean available;

    public MenuItemResponseDto(Long id, String name, double price, boolean available) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }
}
