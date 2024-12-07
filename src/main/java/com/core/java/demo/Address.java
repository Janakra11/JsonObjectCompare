package com.core.java.demo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
public class Address {
    private String permnantAddress;
    private List<Country> countries;

    public Address(){
        super();
    }

    public Address(String permnantAddress, List<Country> countries) {
        this.permnantAddress = permnantAddress;
        this.countries = countries;
    }


    public String getPermnantAddress() {
        return permnantAddress;
    }

    public void setPermnantAddress(String permnantAddress) {
        this.permnantAddress = permnantAddress;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    @Override
    public String toString() {
        return "{\"permnantAddress\":\"" + permnantAddress+"\""+
                ",\"countries\":" + countries +"}";
    }
}
