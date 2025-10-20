using System.Text;
using System.Text.RegularExpressions;

class Program
{
  static void Main()
  {
    Console.OutputEncoding = Encoding.UTF8;
    Console.InputEncoding = Encoding.UTF8;

    Console.Write("Введите строку текста: ");
    string? input = Console.ReadLine();

    if (string.IsNullOrWhiteSpace(input))
    {
      Console.WriteLine("Пустая строка, завершение программы");
      return;
    }

    File.WriteAllText("input.txt", input, Encoding.UTF8);

    string line = File.ReadAllText("input.txt", Encoding.UTF8);

    Console.WriteLine("\nСчитанная строка из файла:");
    Console.WriteLine(line);

    Console.WriteLine("\nСлова, начинающиеся и заканчивающиеся на одну букву:");
    bool found = false;

    string[] words = line.Split(new char[] { ' ', '\t', '\n', '\r' }, StringSplitOptions.RemoveEmptyEntries);

    foreach (string word in words)
    {
      string cleanedWord = Regex.Replace(word, @"^[^\p{L}]+|[^\p{L}]+$", ""); // remove punctuation

      if (cleanedWord.Length > 1)
      {
        char first = char.ToLower(cleanedWord[0]);
        char last = char.ToLower(cleanedWord[cleanedWord.Length - 1]);
        if (first == last)
        {
          Console.WriteLine(cleanedWord);
          found = true;
        }
      }
    }

    if (!found)
      Console.WriteLine("Таких слов не найдено.");
  }
}
