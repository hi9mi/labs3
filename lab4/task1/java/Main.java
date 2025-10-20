import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Product> productList = new ArrayList<>();

        while (true) {
            Product product = new Product();
            product.input(scanner);
            if (product.isExit())
                break;
            productList.add(product);
        }

        System.out.println("\n=== Список товаров ===");
        for (Product product : productList) {
            product.output();
            System.out.println("Цена в долларах: " + product.toUSD(100));
            product.increaseIfToyota(100, 50);
            System.out.print("После проверки: ");
            product.output();
            System.out.println();
        }
    }
}

class Product {
    private String name;
    private double priceRub;
    private String manufacturer;

    public Product() {
    }

    public Product(String name, double priceRub, String manufacturer) {
        this.name = name;
        this.priceRub = priceRub;
        this.manufacturer = manufacturer;
    }

    public void input(Scanner scanner) {
        System.out.print("Введите наименование (0 - выход): ");
        name = scanner.nextLine();
        if (name.isEmpty())
            name = "0";
        if (name.equals("0"))
            return;

        System.out.print("Введите цену (в рублях): ");
        String priceStr = scanner.nextLine();
        if (priceStr.isEmpty())
            priceStr = "0";
        try {
            priceRub = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            priceRub = 0;
        }

        System.out.print("Введите изготовителя: ");
        manufacturer = scanner.nextLine();
        if (manufacturer.isEmpty())
            manufacturer = "Неизвестный изготовитель";
    }

    public void output() {
        System.out.println("Товар: " + name + ", Цена: " + priceRub + " руб., Изготовитель: " + manufacturer);
    }

    public double toUSD(double rate) {
        return priceRub / rate;
    }

    public void increaseIfToyota(double rate, double increase) {
        if (name.toLowerCase(java.util.Locale.ROOT).contains("toyota"))
            priceRub += increase * rate;
    }

    public boolean isExit() {
        return name.equals("0");
    }
}
