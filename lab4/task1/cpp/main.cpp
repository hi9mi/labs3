#include <iostream>
#include <vector>
using namespace std;

class Product
{
private:
    string name;
    double price_rub;
    string manufacturer;

public:
    Product() : name(""), price_rub(0.0), manufacturer("") {}

    Product(string n, double p, string m) : name(move(n)), price_rub(p), manufacturer(move(m)) {}

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
    }

    void output() const
    {
        cout << "Товар: " << name
             << ", Цена: " << price_rub
             << " руб., Изготовитель: " << manufacturer << endl;
    }

    double to_usd(double rate = 100.0) const
    {
        return price_rub / rate;
    }

    void increase_if_toyota(double rate = 100.0, double increase = 50.0)
    {
        if (to_lower_case(name).find("toyota") != string::npos)
            price_rub += increase * rate;
    }

    bool is_exit() const
    {
        return name == "0";
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

    cout << "\n=== Список товаров ===\n";
    for (auto &product : product_list)
    {
        product.output();
        cout << "Цена в долларах: " << product.to_usd() << endl;
        product.increase_if_toyota();
        cout << "После проверки: ";
        product.output();
        cout << endl;
    }

    return 0;
}
