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
            Console.WriteLine($"Цена в долларах: {product.ToUSD()}");
            product.IncreaseIfToyota();
            Console.Write("После проверки: ");
            product.Output();
            Console.WriteLine();
        }
    }
}

class Product
{
    private string name = "";
    private double priceRub;
    private string manufacturer = "";

    public Product() { }

    public Product(string n, double p, string m)
    {
        name = n;
        priceRub = p;
        manufacturer = m;
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
        priceRub = Convert.ToDouble(priceText);

        Console.Write("Введите изготовителя: ");
        var manufacturerText = Console.ReadLine();
        if (string.IsNullOrWhiteSpace(manufacturerText)) manufacturerText = "Неизвестный изготовитель";
        manufacturer = manufacturerText;
    }

    public void Output()
    {
        Console.WriteLine($"Товар: {name}, Цена: {priceRub} руб., Изготовитель: {manufacturer}");
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

    public bool IsExit()
    {
        return name == "0";
    }
}
