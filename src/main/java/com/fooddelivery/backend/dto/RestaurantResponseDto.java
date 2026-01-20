package com.fooddelivery.backend.dto;

public class RestaurantResponseDto {

    private Long id;
    private String name;
    private String location;
    private boolean isOpen;

    public RestaurantResponseDto(Long id, String name, String location, boolean isOpen) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.isOpen = isOpen;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public boolean isOpen() {
        return isOpen;
    }
}

