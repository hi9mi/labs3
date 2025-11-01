#include <iostream>
#include <vector>
#include <fstream>
#include <iostream>
#include <string>
#include <sstream>
using namespace std;

class Product
{
private:
    string name;
    double price_rub{};
    string manufacturer;

public:
    Product() = default;
    Product(string n, double p, string m)
        : name(std::move(n)), price_rub(p), manufacturer(std::move(m)) {}

    void input()
    {
        cout << "Введите наименование (0 - выход): ";
        getline(cin, name);
        if (name.empty())
            name = "0";
        if (name == "0")
            return;

        cout << "Введите цену (в рублях): ";
        string price_str;
        getline(cin, price_str);
        if (price_str.empty())
            price_str = "0";
        try
        {
            price_rub = stod(price_str);
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

    void disk_out(ofstream &out) const
    {
        if (!out)
            throw runtime_error("Ошибка открытия файла для записи");
        out << name << ';' << price_rub << ';' << manufacturer << '\n';
    }

    bool disk_in(ifstream &in)
    {
        string line;
        if (!getline(in, line))
            return false;

        if (line.empty())
            return disk_in(in);

        stringstream ss(line);
        string price_str;

        if (!getline(ss, name, ';'))
            return false;
        if (!getline(ss, price_str, ';'))
            return false;
        if (!getline(ss, manufacturer))
            manufacturer = "Неизвестный изготовитель";

        try
        {
            price_rub = stod(price_str);
        }
        catch (...)
        {
            price_rub = 0;
        }

        return true;
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

    try
    {

        while (true)
        {
            Product product;
            product.input();
            if (product.is_exit())
                break;
            product_list.push_back(product);
        }

        {
            ofstream out("products.txt");
            if (!out.is_open())
                throw runtime_error("Не удалось открыть файл для записи");

            for (const auto &product : product_list)
                product.disk_out(out);

            cout << "\nДанные успешно записаны в файл products.txt\n";
        }

        vector<Product> loaded_products;
        {
            ifstream in("products.txt");
            if (!in.is_open())
                throw runtime_error("Не удалось открыть файл для чтения");

            Product product;
            while (product.disk_in(in))
            {
                loaded_products.push_back(product);
            }
        }

        cout << "\n=== Считано из файла ===\n";
        for (auto &product : loaded_products)
        {
            product.output();
            cout << "Цена в долларах: " << product.to_usd() << endl;
            product.increase_if_toyota();
            cout << "После проверки: ";
            product.output();
            cout << endl;
        }
    }
    catch (const exception &e)
    {
        cerr << "[Ошибка]: " << e.what() << endl;
        return 1;
    }

    return 0;
}
