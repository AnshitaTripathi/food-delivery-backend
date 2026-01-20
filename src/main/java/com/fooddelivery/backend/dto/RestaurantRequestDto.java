package com.fooddelivery.backend.dto;

import jakarta.validation.constraints.NotBlank;

public class RestaurantRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String location;

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

