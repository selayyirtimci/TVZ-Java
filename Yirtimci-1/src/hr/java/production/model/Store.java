package hr.java.production.model;

import java.util.List;

public class Store {
    private String name;
    private String webAddress;
    private List<Item> items;

    public Store(String name, String webAddress, List<Item> items) {
        this.name = name;
        this.webAddress = webAddress;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebAddress() {
        return webAddress;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
