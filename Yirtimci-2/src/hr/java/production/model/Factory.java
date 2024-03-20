package hr.java.production.model;

import java.util.List;

public class Factory extends NamedEntity {
    private Address address;
    private Item[] items;

    public Factory(String name, Address address, Item[] items) {
        super(name);
        this.address = address;
        this.items = items;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }
}
