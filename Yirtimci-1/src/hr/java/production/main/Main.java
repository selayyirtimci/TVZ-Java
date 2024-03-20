// Package hr.java.production.main
package hr.java.production.main;

import hr.java.production.model.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Task 11
        List<Category> categories = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            categories.add(inputCategory());
        }

        List<Item> items = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            items.add(inputItem(categories));
        }

        List<Factory> factories = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            factories.add(inputFactory(items));
        }

        List<Store> stores = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            stores.add(inputStore(items));
        }

        // Task 12
        Factory factoryWithLargestItem = findFactoryWithLargestItem(factories);
        Store storeWithCheapestItem = findStoreWithCheapestItem(stores);

        System.out.println("Factory with largest item: " + factoryWithLargestItem.getName());
        System.out.println("Store with cheapest item: " + storeWithCheapestItem.getName());
    }

    // Task 13
    private static Category inputCategory() {
        System.out.println("Enter category name:");
        String name = scanner.nextLine();
        System.out.println("Enter category description:");
        String description = scanner.nextLine();
        return new Category(name, description);
    }

    private static Item inputItem(List<Category> categories) {
        System.out.println("Enter item name:");
        String name = scanner.nextLine();
        System.out.println("Enter item category (choose a number):");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getName());
        }
        int categoryIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // consume newline left-over
        Category category = categories.get(categoryIndex);
        System.out.println("Enter item width:");
        BigDecimal width = scanner.nextBigDecimal();
        scanner.nextLine(); // consume newline left-over
        System.out.println("Enter item height:");
        BigDecimal height = scanner.nextBigDecimal();
        scanner.nextLine(); // consume newline left-over
        System.out.println("Enter item length:");
        BigDecimal length = scanner.nextBigDecimal();
        scanner.nextLine(); // consume newline left-over
        System.out.println("Enter item production cost:");
        BigDecimal productionCost = scanner.nextBigDecimal();
        scanner.nextLine(); // consume newline left-over
        System.out.println("Enter item selling price:");
        BigDecimal sellingPrice = scanner.nextBigDecimal();
        scanner.nextLine(); // consume newline left-over
        return new Item(name, category, width, height, length, productionCost, sellingPrice);
    }

    private static Factory inputFactory(List<Item> items) {
        System.out.println("Enter factory name:");
        String name = scanner.nextLine();
        System.out.println("Enter factory address:");
        Address address = inputAddress();
        System.out.println("Enter factory items (choose numbers, separated by space):");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i).getName());
        }
        String[] itemIndexes = scanner.nextLine().split(" ");
        List<Item> factoryItems = new ArrayList<>();
        for (String itemIndex : itemIndexes) {
            factoryItems.add(items.get(Integer.parseInt(itemIndex) - 1));
        }
        return new Factory(name, address, factoryItems);
    }

    private static Store inputStore(List<Item> items) {
        System.out.println("Enter store name:");
        String name = scanner.nextLine();
        System.out.println("Enter store web address:");
        String webAddress = scanner.nextLine();
        System.out.println("Enter store items (choose numbers, separated by space):");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i).getName());
        }
        String[] itemIndexes = scanner.nextLine().split(" ");
        List<Item> storeItems = new ArrayList<>();
        for (String itemIndex : itemIndexes) {
            storeItems.add(items.get(Integer.parseInt(itemIndex) - 1));
        }
        return new Store(name, webAddress, storeItems);
    }

    private static Address inputAddress() {
        System.out.println("Enter street:");
        String street = scanner.nextLine();
        System.out.println("Enter house number:");
        String houseNumber = scanner.nextLine();
        System.out.println("Enter city:");
        String city = scanner.nextLine();
        System.out.println("Enter postal code:");
        String postalCode = scanner.nextLine();
        return new Address(street, houseNumber, city, postalCode);
    }

    private static Factory findFactoryWithLargestItem(List<Factory> factories) {
        Factory factoryWithLargestItem = null;
        BigDecimal maxVolume = BigDecimal.ZERO;
        for (Factory factory : factories) {
            for (Item item : factory.getItems()) {
                BigDecimal volume = item.getWidth().multiply(item.getHeight()).multiply(item.getLength());
                if (volume.compareTo(maxVolume) > 0) {
                    maxVolume = volume;
                    factoryWithLargestItem = factory;
                }
            }
        }
        return factoryWithLargestItem;
    }

    private static Store findStoreWithCheapestItem(List<Store> stores) {
        Store storeWithCheapestItem = null;
        BigDecimal minPrice = BigDecimal.valueOf(Double.MAX_VALUE);
        for (Store store : stores) {
            for (Item item : store.getItems()) {
                if (item.getSellingPrice().compareTo(minPrice) < 0) {
                    minPrice = item.getSellingPrice();
                    storeWithCheapestItem = store;
                }
            }
        }
        return storeWithCheapestItem;
    }
}
