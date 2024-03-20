package hr.java.production.main;

import hr.java.production.model.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Integer VEGETABLE = 1, FRUIT = 2;
    private static final Integer FOOD = 1, LAPTOP = 2;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        List<Category> categories = new ArrayList<>();
        List<Item> items = new ArrayList<>();
        List<Factory> factories = new ArrayList<>();
        List<Store> stores = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            categories.add(inputCategory());
        }

        for (int i = 0; i < 5; i++) {
            inputItem(categories, items);
        }

        for (int i = 0; i < 2; i++) {
            factories.add(inputFactory(items));
        }

        for (int i = 0; i < 2; i++) {
            stores.add(inputStore(items));
        }

        // Task 12
        Factory factoryWithLargestItem = findFactoryWithLargestItem(factories);
        Store storeWithCheapestItem = findStoreWithCheapestItem(stores);

        System.out.println("Factory with largest item: " + factoryWithLargestItem.getName());
        System.out.println("Store with cheapest item: " + storeWithCheapestItem.getName());


        Item mostCaloricFood = findMostCaloricFood(items);
        if (mostCaloricFood instanceof Edible e)
            System.out.println("The food product with the most calories is " + mostCaloricFood.getName() + " [" + e.calculateKilocalories() + "]");

        Item highestPricedFood = findHighestPricedFood(items);
        if (highestPricedFood instanceof Edible e)
            System.out.println("The food product with the highest price (with discount) is " + highestPricedFood.getName() + " [" + e.calculatePrice() + "]");

        Item shortestWarrantyLaptop = findLaptopWithShortestWarranty(items);
        if (shortestWarrantyLaptop instanceof Technical t)
            System.out.println("The laptop with the shortest warranty is " + shortestWarrantyLaptop.getName() + " [" + t.getRemainingWarrantyInMonths() + "]");

        System.out.println("Finished");
    }
    private static Item findLaptopWithShortestWarranty(List<Item> items) {
        Item shortestWarrantyLaptop = items.get(0);
        Integer minWarranty = Integer.MAX_VALUE;

        for (Item item : items) {
            if (item instanceof Technical t) {
                Integer warranty = t.getRemainingWarrantyInMonths();
                if (warranty < minWarranty) {
                    minWarranty = warranty;
                    shortestWarrantyLaptop = item;
                }
            }
        }
        if (minWarranty == Integer.MAX_VALUE) System.out.println("[ERROR] There are no laptops among items. Returning the first item in the list.");
        return shortestWarrantyLaptop;
    }

    private static Item findHighestPricedFood(List<Item> items) {
        Item mostExpensive = items.get(0);
        BigDecimal highestPrice = BigDecimal.valueOf(-1);
        for (Item item : items) {
            if (item instanceof Edible edible) {
                BigDecimal price = edible.calculatePrice();
                if (price.compareTo(highestPrice) > 0) {
                    highestPrice = price;
                    mostExpensive = item;
                }
            }
        }
        if (highestPrice.equals(BigDecimal.valueOf(-1))) System.out.println("[ERROR] There are no food products among items. Returning the first item in the list.");
        return mostExpensive;
    }

    private static Item findMostCaloricFood(List<Item> items) {
        Item mostCaloric = items.get(0);
        int maxCalories = -1;
        for (Item item : items) {
            if (item instanceof Edible edible) {
                int calories = edible.calculateKilocalories();
                if (calories > maxCalories) {
                    maxCalories = calories;
                    mostCaloric = item;
                }
            }
        }
        if (maxCalories == -1) System.out.println("[ERROR] There are no food products among items. Returning the first item in the list.");
        return mostCaloric;
    }


    // Task 13
    private static Category inputCategory() {
        System.out.println("Enter category name:");
        String name = scanner.nextLine();
        System.out.println("Enter category description:");
        String description = scanner.nextLine();
        return new Category(name, description);
    }

    private static Item inputItem(List<Category> categories, List<Item> items) {
        Item newItem = null;
        System.out.println("Enter item name:");
        String name = scanner.nextLine();
        System.out.println("Enter item category (choose a number):");

        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getName());
        }

        int categoryIndex = numInputHandler(scanner, "Choose a category (1-" + categories.size() + "): ", 1, categories.size()) - 1;
        Category category = categories.get(categoryIndex);

        System.out.println("Enter item width:");
        BigDecimal width = scanner.nextBigDecimal();
        scanner.nextLine();
        System.out.println("Enter item height:");
        BigDecimal height = scanner.nextBigDecimal();
        scanner.nextLine();
        System.out.println("Enter item length:");
        BigDecimal length = scanner.nextBigDecimal();
        scanner.nextLine();
        System.out.println("Enter item production cost:");
        BigDecimal productionCost = scanner.nextBigDecimal();
        scanner.nextLine();
        System.out.println("Enter item selling price:");
        BigDecimal sellingPrice = scanner.nextBigDecimal();
        scanner.nextLine();
        Discount discount = inputDiscount();

        int itemSubclassChoice = numInputHandler(scanner, "Is this item food, a laptop, or other:\n1. Food\n2. Laptop\n3. Other\nChoice > ", 1, 3);

        if (itemSubclassChoice == FOOD) {
            Integer foodChoice = numInputHandler(scanner, "Pick an available food product:\n1. Vegetable\n2. Fruit\nChoice > ", VEGETABLE, FRUIT);
            BigDecimal weightInKG = numInputHandler(scanner, "Enter the weight (in kg) of the food packet: ", BigDecimal.valueOf(1E-99), BigDecimal.valueOf(1E+99));

            if (foodChoice.equals(VEGETABLE))
                newItem = new Vegetable(name, category, width, height, length, productionCost, sellingPrice, discount, weightInKG);
            else if (foodChoice.equals(FRUIT))
                newItem = new Fruit(name, category, width, height, length, productionCost, sellingPrice, discount, weightInKG);
        } else if (itemSubclassChoice == LAPTOP) {
            Integer warrantyYears = numInputHandler(scanner, "Enter the warranty duration (in years) of the laptop: ", 0, 100);
            newItem = new Laptop(name, category, width, height, length, productionCost, sellingPrice, discount, warrantyYears);
        } else {
            newItem = new Item(name, category, width, height, length, productionCost, sellingPrice, discount);
        }

        if (newItem != null) {
            items.add(newItem);

            if (newItem instanceof Edible e) {
                System.out.println("Kilocalories in " + newItem.getName() + ": " + e.calculateKilocalories());
                System.out.println("Price (with " + newItem.getDiscount().discountAmount() + "% discount) for " + newItem.getName() + ": " + e.calculatePrice());
            }
        } else {
            System.out.println("[ERROR] Item creation failed.");
        }
        return newItem;
    }

    private static Discount inputDiscount() {
        System.out.println("Enter discount amount:");
        double discountAmount = scanner.nextDouble();
        scanner.nextLine(); // consume newline left-over
        return new Discount(BigDecimal.valueOf(discountAmount));
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
        return new Factory(name, address, factoryItems.toArray(new Item[0]));
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
        return new Store(name, webAddress, storeItems.toArray(new Item[0]));
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
        return new Address.Builder().atStreet(street).atHouseNumber(houseNumber).atCity(city).atPostalCode(postalCode).build();
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

    private static int numInputHandler(Scanner scanner, String prompt, int min, int max) {
        int input;
        while (true) {
            try {
                System.out.print(prompt);
                input = Integer.parseInt(scanner.nextLine());
                if (input < min || input > max) {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
        return input;
    }

    private static BigDecimal numInputHandler(Scanner scanner, String prompt, BigDecimal min, BigDecimal max) {
        BigDecimal input;
        while (true) {
            try {
                System.out.print(prompt);
                input = new BigDecimal(scanner.nextLine());
                if (input.compareTo(min) < 0 || input.compareTo(max) > 0) {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                } else {
                    break;
                }
            } catch (NumberFormatException | ArithmeticException e) {
                System.out.println("Please enter a valid number.");
            }
        }
        return input;
    }
}
