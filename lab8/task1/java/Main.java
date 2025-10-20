import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    enum CarType {
        SEDAN(1, "Седан"),
        SUV(2, "Внедорожник"),
        HATCHBACK(3, "Хэтчбек"),
        TRUCK(4, "Грузовик"),
        OTHER(5, "Другое");

        final int code;
        final String title;

        CarType(int code, String title) {
            this.code = code;
            this.title = title;
        }

        static CarType fromCode(int code) {
            for (CarType c : values())
                if (c.code == code)
                    return c;
            return OTHER;
        }

        @Override
        public String toString() {
            return title;
        }
    }

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
            System.out.println("Цена в долларах: " + String.format(Locale.US, "%.2f", product.toUSD(100)));
            product.increaseIfToyota(100, 50);
            System.out.print("После проверки: ");
            product.output();
            System.out.println();
        }

        System.out.println("=== Поиск по типу автомобиля ===");
        System.out.println(" 1 - Седан, 2 - Внедорожник, 3 - Хэтчбек, 4 - Грузовик, 5 - Другое");
        System.out.print("Ваш выбор: ");
        int code = 5;
        try {
            code = Integer.parseInt(scanner.nextLine().trim());
        } catch (Exception ignored) {
        }
        CarType query = CarType.fromCode(code);

        System.out.println("\nТовары типа \"" + query + "\":");
        boolean found = false;
        for (Product product : productList) {
            if (product.getCarType() == query) {
                product.output();
                found = true;
            }
        }
        if (!found)
            System.out.println("Не найдено.");

        productList.sort(Comparator.comparing(Product::getCreationDate));
        System.out.println("\n=== Отсортировано по дате создания ===");
        for (Product product : productList)
            product.output();
    }
}

class Product {
    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd.MM.yyyy");
    static {
        SDF.setLenient(false);
    }
    private String name;
    private double priceRub;
    private String manufacturer;
    private Main.CarType carType = Main.CarType.OTHER;
    private Calendar creationDate = Calendar.getInstance();

    public Product() {
    }

    public Product(String name, double priceRub, String manufacturer, Main.CarType carType, Calendar creationDate) {
        this.name = name;
        this.priceRub = priceRub;
        this.manufacturer = manufacturer;
        this.carType = (carType == null ? Main.CarType.OTHER : carType);
        this.creationDate = (creationDate == null ? Calendar.getInstance() : creationDate);
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

        System.out.println("Выберите тип автомобиля:");
        System.out.println(" 1 - Седан");
        System.out.println(" 2 - Внедорожник");
        System.out.println(" 3 - Хэтчбек");
        System.out.println(" 4 - Грузовик");
        System.out.println(" 5 - Другое");
        System.out.print("Введите номер: ");
        int code = 5;
        try {
            code = Integer.parseInt(scanner.nextLine().trim());
        } catch (Exception ignored) {
        }
        if (code < 1 || code > 5)
            code = 5;
        carType = Main.CarType.fromCode(code);

        System.out.print("Введите дату создания (дд.мм.гггг) или оставьте пустым для текущей: ");
        String dateStr = scanner.nextLine().trim();
        if (dateStr.isEmpty()) {
            creationDate = Calendar.getInstance();
        } else {
            Calendar parsed = parseDateOrNull(dateStr);
            if (parsed == null) {
                System.out.println("Некорректный формат даты — установлена текущая дата.");
                creationDate = Calendar.getInstance();
            } else {
                creationDate = parsed;
            }
        }
    }

    public void output() {
        System.out.println("Товар: " + name
                + ", Цена: " + priceRub + " руб."
                + ", Изготовитель: " + manufacturer
                + ", Тип: " + carType
                + ", Дата создания: " + SDF.format(creationDate.getTime()));
    }

    public double toUSD(double rate) {
        return priceRub / rate;
    }

    public void increaseIfToyota(double rate, double increase) {
        if (name != null && name.toLowerCase(java.util.Locale.ROOT).contains("toyota"))
            priceRub += increase * rate;
    }

    public boolean isExit() {
        return "0".equals(name);
    }

    public Main.CarType getCarType() {
        return carType;
    }

    public Calendar getCreationDate() {
        return creationDate;
    }

    private static Calendar parseDateOrNull(String s) {
        try {
            Date d = SDF.parse(s);
            Calendar c = Calendar.getInstance();
            c.setTime(d);
            return c;
        } catch (ParseException e) {
            return null;
        }
    }
}
