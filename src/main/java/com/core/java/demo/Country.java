package com.core.java.demo;

import lombok.Data;
import lombok.ToString;

@Data

public class Country {
    private String city;
    private String state;

    public Country(){
        super();
    }

    public Country(String city, String state) {
        this.city = city;
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "{\"city\":\"" + city + "\"" +
                ", \"state\":\"" + state + "\"}";
    }
}
