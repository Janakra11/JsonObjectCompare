package com.core.java.demo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
public class Employee {

    private Long employeeId;
    private String name;
    private String desc;

    private List<Address> address;

    public Employee() {
       super();
    }

    public Employee(Long employeeId, String name, String desc, List<Address> address) {
        this.address = address;
        this.desc = desc;
        this.name = name;
        this.employeeId = employeeId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "{\"employeeId\":\"" + employeeId +"\""+
                ", \"name\":\"" + name + "\"" +
                ", \"desc\":\"" + desc + "\"" +
                ", \"address\":" + address +"}";
    }
}
