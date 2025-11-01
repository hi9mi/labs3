using System.Globalization;

class Program
{
    static void Main()
    {
        List<Product> productList = [];

        while (true)
        {
            Product product = new Product();
            product.Input();
            if (product.IsExit()) break;
            productList.Add(product);
        }

        Console.WriteLine("\n=== Список товаров ===");
        foreach (var product in productList)
        {
            product.Output();
            Console.WriteLine($"Цена в долларах: {product.ToUSD():F2}");
            product.IncreaseIfToyota();
            Console.Write("После проверки: ");
            product.Output();
            Console.WriteLine();
        }

        Console.WriteLine("=== Поиск по типу автомобиля ===");
        Console.WriteLine(" 1 - Седан, 2 - Внедорожник, 3 - Хэтчбек, 4 - Грузовик, 5 - Другое");
        Console.Write("Введите номер типа: ");
        if (!int.TryParse(Console.ReadLine(), out int code)) code = 5;
        if (code < 1 || code > 5) code = 5;
        CarType queryType = (CarType)code;

        Console.WriteLine($"\nРезультаты поиска по типу \"{queryType}\":");
        bool found = false;
        foreach (var p in productList)
        {
            if (p.Type == queryType)
            {
                p.Output();
                found = true;
            }
        }
        if (!found)
            Console.WriteLine("Не найдено");

        productList.Sort((a, b) => a.CreationDate.CompareTo(b.CreationDate));

        Console.WriteLine("\n=== Отсортировано по дате создания ===");
        foreach (var p in productList)
            p.Output();
    }
}

enum CarType
{
    Sedan = 1,
    SUV,
    Hatchback,
    Truck,
    Other
}

class Product
{
    private string name = "";
    private double priceRub;
    private string manufacturer = "";

    public CarType Type { get; private set; } = CarType.Other;
    public DateTime CreationDate { get; private set; } = DateTime.Now;

    public Product() { }

    public Product(string n, double p, string m, CarType type, DateTime creationDate)
    {
        name = n;
        priceRub = p;
        manufacturer = m;
        Type = type;
        CreationDate = creationDate;
    }

    public void Input()
    {
        Console.Write("Введите наименование (0 - выход): ");
        var nameText = Console.ReadLine();
        if (string.IsNullOrWhiteSpace(nameText)) nameText = "0";
        name = nameText;
        if (name == "0") return;

        Console.Write("Введите цену (в рублях): ");
        var priceText = Console.ReadLine();
        if (string.IsNullOrWhiteSpace(priceText)) priceText = "0";
        double.TryParse(priceText, out priceRub);

        Console.Write("Введите изготовителя: ");
        var manufacturerText = Console.ReadLine();
        if (string.IsNullOrWhiteSpace(manufacturerText)) manufacturerText = "Неизвестный изготовитель";
        manufacturer = manufacturerText;

        Console.WriteLine("Выберите тип автомобиля:");
        Console.WriteLine(" 1 - Седан");
        Console.WriteLine(" 2 - Внедорожник");
        Console.WriteLine(" 3 - Хэтчбек");
        Console.WriteLine(" 4 - Грузовик");
        Console.WriteLine(" 5 - Другое");
        Console.Write("Введите номер: ");
        if (!int.TryParse(Console.ReadLine(), out int typeCode)) typeCode = 5;
        if (typeCode < 1 || typeCode > 5) typeCode = 5;
        Type = (CarType)typeCode;

        Console.Write("Введите дату создания (дд.ММ.гггг) или оставьте пустым для текущей: ");
        var dateText = Console.ReadLine();
        if (string.IsNullOrWhiteSpace(dateText))
        {
            CreationDate = DateTime.Now;
        }
        else
        {
            if (DateTime.TryParseExact(dateText, "dd.MM.yyyy", CultureInfo.InvariantCulture,
                                       DateTimeStyles.None, out DateTime parsed))
                CreationDate = parsed;
            else
            {
                Console.WriteLine("Некорректный ввод — установлена текущая дата.");
                CreationDate = DateTime.Now;
            }
        }
    }

    public void Output()
    {
        Console.WriteLine($"Товар: {name}, Цена: {priceRub} руб., Изготовитель: {manufacturer}, " +
                          $"Тип: {Type}, Дата создания: {CreationDate:dd.MM.yyyy}");
    }

    public double ToUSD(double rate = 100.0)
    {
        return priceRub / rate;
    }

    public void IncreaseIfToyota(double rate = 100.0, double increase = 50.0)
    {
        if (name.Contains("toyota", StringComparison.CurrentCultureIgnoreCase))
            priceRub += increase * rate;
    }

    public bool IsExit() => name == "0";
}
