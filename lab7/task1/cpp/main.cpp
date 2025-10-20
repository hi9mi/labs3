#include <iostream>
#include <fstream>
#include <sstream>
#include <locale>
#include <codecvt>
using namespace std;

bool is_same_start_end(const wstring &word)
{
    if (word.empty())
        return false;
    wchar_t first = towlower(word.front());
    wchar_t last = towlower(word.back());
    return first == last;
}

int main()
{
    locale::global(locale(""));

    wstring input;
    wcout << L"Введите строку текста: ";
    getline(wcin, input);

    wofstream outFile("input.txt");
    outFile.imbue(locale(outFile.getloc(), new codecvt_utf8<wchar_t>));
    if (!outFile.is_open())
    {
        wcerr << L"Ошибка: не удалось создать файл input.txt" << endl;
        return 1;
    }
    outFile << input;
    outFile.close();

    wifstream inFile("input.txt");
    inFile.imbue(locale(inFile.getloc(), new codecvt_utf8<wchar_t>));
    if (!inFile.is_open())
    {
        wcerr << L"Ошибка: не удалось открыть файл input.txt" << endl;
        return 1;
    }

    wstring line;
    getline(inFile, line);
    inFile.close();

    wcout << L"\nСчитанная строка из файла:\n"
          << line << endl;

    wstringstream ss(line);
    wstring word;
    bool found = false;

    wcout << L"\nСлова, начинающиеся и заканчивающиеся на одну букву:\n";

    while (ss >> word)
    {
        while (!word.empty() && iswpunct(word.front()))
            word.erase(word.begin());
        while (!word.empty() && iswpunct(word.back()))
            word.pop_back();

        if (is_same_start_end(word))
        {
            wcout << word << endl;
            found = true;
        }
    }

    if (!found)
        wcout << L"Таких слов не найдено." << endl;

    return 0;
}
