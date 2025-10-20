import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Product> productList = new ArrayList<>();

        try {
            while (true) {
                Product product = new Product();
                product.input(scanner);
                if (product.isExit())
                    break;
                productList.add(product);
            }

            String fileName = "products.txt";
            try (BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8))) {
                for (Product product : productList) {
                    product.diskOut(writer);
                }
            }
            System.out.println("\nДанные успешно записаны в файл " + fileName);

            ArrayList<Product> loadedProducts = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty())
                        continue;
                    try {
                        Product product = new Product();
                        product.diskIn(line);
                        loadedProducts.add(product);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Пропущен объект: " + e.getMessage());
                    }
                }
            }

            System.out.println("\n=== Считано из файла ===");
            for (Product product : loadedProducts) {
                product.output();
                System.out.println("Цена в долларах: " + product.toUSD(100));
                product.increaseIfToyota(100, 50);
                System.out.print("После проверки: ");
                product.output();
                System.out.println();
            }

        } catch (IOException e) {
            System.err.println("[Ошибка ввода/вывода]: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("[Ошибка]: " + e.getMessage());
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
            priceRub = Double.parseDouble(priceStr.replace(',', '.'));
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
        if (name.toLowerCase(Locale.ROOT).contains("toyota"))
            priceRub += increase * rate;
    }

    public boolean isExit() {
        return name.equals("0");
    }

    public void diskOut(BufferedWriter writer) throws IOException {
        writer.write(name + ";" + priceRub + ";" + manufacturer);
        writer.newLine();
    }

    public void diskIn(String line) {
        String[] parts = line.split(";");
        if (parts.length < 3)
            throw new IllegalArgumentException("Неверный формат строки: " + line);

        name = parts[0].trim();

        try {
            priceRub = Double.parseDouble(parts[1].trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Неверный формат цены: " + parts[1]);
        }

        manufacturer = parts[2].trim();
    }
}
