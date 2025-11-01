using System.Globalization;
using System.Text;

class Program
{
    static void Main()
    {
        var productList = new List<Product>();

        try
        {
            while (true)
            {
                var product = new Product();
                product.Input();
                if (product.IsExit()) break;
                productList.Add(product);
            }

            const string fileName = "products.txt";
            using (var sw = new StreamWriter(fileName, false, new UTF8Encoding(false)))
            {
                foreach (var product in productList)
                    product.DiskOut(sw);
            }
            Console.WriteLine($"\n Данные записаны в файл: {fileName}");

            var loadedProducts = new List<Product>();
            using (var sr = new StreamReader(fileName, Encoding.UTF8))
            {
                while (!sr.EndOfStream)
                {
                    var product = new Product();
                    try
                    {
                        product.DiskIn(sr);
                        loadedProducts.Add(product);
                    }
                    catch (EndOfStreamException)
                    {
                        break;
                    }
                    catch (FormatException fe)
                    {
                        Console.Error.WriteLine("Пропущен объект: " + fe.Message);
                    }
                }
            }

            Console.WriteLine("\n=== Считано из файла ===");
            foreach (var product in loadedProducts)
            {
                product.Output();
                Console.WriteLine($"Цена в долларах: {product.ToUSD()}");
                product.IncreaseIfToyota();
                Console.Write("После проверки: ");
                product.Output();
                Console.WriteLine();
            }
        }
        catch (IOException io)
        {
            Console.Error.WriteLine("[Ошибка ввода/вывода] " + io.Message);
        }
        catch (Exception ex)
        {
            Console.Error.WriteLine("[Ошибка] " + ex.Message);
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

        if (!double.TryParse(priceText.Replace(',', '.'),
                             NumberStyles.Any,
                             CultureInfo.InvariantCulture,
                             out priceRub))
        {
            if (!double.TryParse(priceText,
                                 NumberStyles.Any,
                                 CultureInfo.CurrentCulture,
                                 out priceRub))
                priceRub = 0;
        }

        Console.Write("Введите изготовителя: ");
        var manufacturerText = Console.ReadLine();
        manufacturer = string.IsNullOrWhiteSpace(manufacturerText)
            ? "Неизвестный изготовитель"
            : manufacturerText;
    }

    public void Output()
    {
        Console.WriteLine($"Товар: {name}, Цена: {priceRub} руб., Изготовитель: {manufacturer}");
    }

    public double ToUSD(double rate = 100.0) => priceRub / rate;

    public void IncreaseIfToyota(double rate = 100.0, double increase = 50.0)
    {
        if (name.Contains("toyota", StringComparison.OrdinalIgnoreCase))
            priceRub += increase * rate;
    }

    public bool IsExit() => name == "0";

    public void DiskOut(StreamWriter sw)
    {
        if (sw == null) throw new IOException("Поток записи не инициализирован");
        string line = $"{name};{priceRub.ToString(CultureInfo.InvariantCulture)};{manufacturer}";
        sw.WriteLine(line);
    }

    public void DiskIn(StreamReader sr)
    {
        if (sr == null) throw new IOException("Поток чтения не инициализирован");
        string? line = sr.ReadLine();

        if (string.IsNullOrWhiteSpace(line))
            throw new EndOfStreamException();

        var parts = line.Split(';');
        if (parts.Length < 3)
            throw new FormatException("Некорректный формат строки: " + line);

        name = parts[0].Trim();

        if (!double.TryParse(parts[1], NumberStyles.Any, CultureInfo.InvariantCulture, out priceRub))
            throw new FormatException($"Неверный формат цены: '{parts[1]}'");

        manufacturer = parts[2].Trim();
    }
}
