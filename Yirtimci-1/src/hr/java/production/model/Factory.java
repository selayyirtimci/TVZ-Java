package hr.java.production.model;

import java.util.List;

public class Factory {
    private String name;
    private Address address;
    private List<Item> items;

    public Factory(String name, Address address, List<Item> items) {
        this.name = name;
        this.address = address;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
