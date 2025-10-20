#include <iostream>
#include <vector>
#include <string>
#include <algorithm>
#include <iomanip>
#include <ctime>
#include <sstream>
using namespace std;

class Product
{
public:
    enum class CarType
    {
        Sedan = 1,
        SUV,
        Hatchback,
        Truck,
        Other
    };

private:
    string name;
    double price_rub;
    string manufacturer;
    tm creation_date{};
    CarType type = CarType::Other;

public:
    Product()
        : name(""), price_rub(0.0), manufacturer("")
    {
        time_t now = time(nullptr);
        creation_date = *localtime(&now);
    }

    static const char *type_to_string(CarType t)
    {
        switch (t)
        {
        case CarType::Sedan:
            return "Седан";
        case CarType::SUV:
            return "Внедорожник";
        case CarType::Hatchback:
            return "Хэтчбек";
        case CarType::Truck:
            return "Грузовик";
        default:
            return "Другое";
        }
    }

    void input()
    {
        cout << "Введите наименование (0 - выход): ";
        getline(cin, name);
        if (name.empty())
            name = "0";
        if (name == "0")
            return;

        cout << "Введите цену (в рублях): ";
        string input_str;
        getline(cin, input_str);
        if (input_str.empty())
            input_str = "0";
        try
        {
            price_rub = stod(input_str);
        }
        catch (...)
        {
            price_rub = 0;
        }

        cout << "Введите изготовителя: ";
        getline(cin, manufacturer);
        if (manufacturer.empty())
            manufacturer = "Неизвестный изготовитель";

        cout << "Выберите тип автомобиля:\n"
                "  1 - Седан\n"
                "  2 - Внедорожник (SUV)\n"
                "  3 - Хэтчбек\n"
                "  4 - Грузовик\n"
                "  5 - Другое\n"
                "Введите номер: ";
        string tline;
        getline(cin, tline);
        int tnum = 5;
        try
        {
            tnum = stoi(tline);
        }
        catch (...)
        {
            tnum = 5;
        }
        if (tnum < 1 || tnum > 5)
            tnum = 5;
        type = static_cast<CarType>(tnum);

        cout << "Введите дату создания (дд мм гггг) или оставьте пустым для текущей: ";
        string line;
        getline(cin, line);
        if (line.empty())
        {
            time_t now = time(nullptr);
            creation_date = *localtime(&now);
        }
        else
        {
            int d, m, y;
            istringstream iss(line);
            if (iss >> d >> m >> y)
            {
                creation_date = {};
                creation_date.tm_mday = d;
                creation_date.tm_mon = m - 1;
                creation_date.tm_year = y - 1900;
                mktime(&creation_date);
            }
            else
            {
                cout << "Некорректный ввод — установлена текущая дата.\n";
                time_t now = time(nullptr);
                creation_date = *localtime(&now);
            }
        }
    }

    void output() const
    {
        cout << "Товар: " << name
             << ", Цена: " << price_rub
             << " руб., Изготовитель: " << manufacturer
             << ", Тип: " << type_to_string(type)
             << ", Дата создания: " << put_time(&creation_date, "%d.%m.%Y")
             << endl;
    }

    double to_usd(double rate = 100.0) const { return price_rub / rate; }

    void increase_if_toyota(double rate = 100.0, double increase = 50.0)
    {
        if (to_lower_case(name).find("toyota") != string::npos)
            price_rub += increase * rate;
    }

    bool is_exit() const
    {
        return name == "0";
    }

    time_t date_as_time_t() const
    {
        tm temp = creation_date;
        return mktime(&temp);
    }

    CarType get_type() const
    {
        return type;
    }

    static string to_lower_case(string str)
    {
        for (char &c : str)
            c = static_cast<char>(tolower(static_cast<unsigned char>(c)));
        return str;
    }
};

int main()
{
    vector<Product> product_list;

    while (true)
    {
        Product product;
        product.input();
        if (product.is_exit())
            break;
        product_list.push_back(product);
    }

    if (product_list.empty())
    {
        cout << "Список пуст.\n";
        return 0;
    }

    cout << "\n=== Список товаров ===\n";
    for (auto &p : product_list)
    {
        p.output();
        cout << "Цена в долларах: " << p.to_usd() << endl;
        p.increase_if_toyota();
        cout << "После проверки: ";
        p.output();
        cout << endl;
    }

    cout << "=== Поиск по типу автомобиля ===\n"
            "  1 - Седан, 2 - Внедорожник, 3 - Хэтчбек, 4 - Грузовик, 5 - Другое\n"
            "Ваш выбор: ";
    string s;
    getline(cin, s);
    int q = 5;
    try
    {
        q = stoi(s);
    }
    catch (...)
    {
        q = 5;
    }
    if (q < 1 || q > 5)
        q = 5;
    Product::CarType query = static_cast<Product::CarType>(q);

    cout << "\nРезультаты по типу \"" << Product::type_to_string(query) << "\":\n";
    bool found = false;
    for (const auto &p : product_list)
    {
        if (p.get_type() == query)
        {
            p.output();
            found = true;
        }
    }
    if (!found)
        cout << "Не найдено\n";

    sort(product_list.begin(), product_list.end(),
         [](const Product &a, const Product &b)
         { return a.date_as_time_t() < b.date_as_time_t(); });

    cout << "\n=== Отсортировано по дате создания ===\n";
    for (const auto &product : product_list)
        product.output();

    return 0;
}
