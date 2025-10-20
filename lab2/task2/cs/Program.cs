class Program
{
  static void Main()
  {
    int K = ReadPositiveInt("Введите K (кол-во вагонов): ");
    int M = ReadPositiveInt("Введите M (кол-во мест в вагоне): ");
    int[,] sold = new int[K, M];

    int N = ReadNonNegativeInt("Введите N (кол-во проданных билетов): ");
    ReadSoldPairs(sold, N);

    PrintSummary(sold);
    QueryLoop(sold);
  }

  static int ReadPositiveInt(string prompt)
  {
    while (true)
    {
      Console.Write(prompt);
      string? s = Console.ReadLine();
      if (int.TryParse(s, out int v) && v > 0) return v;
      Console.WriteLine("Введите положительное целое число");
    }
  }

  static int ReadNonNegativeInt(string prompt)
  {
    while (true)
    {
      Console.Write(prompt);
      string? s = Console.ReadLine();
      if (int.TryParse(s, out int v) && v >= 0) return v;
      Console.WriteLine("Введите неотрицательное целое число");
    }
  }

  static void ReadSoldPairs(int[,] sold, int N)
  {
    int K = sold.GetLength(0);
    int M = sold.GetLength(1);

    Console.WriteLine("Введите пары <вагон место> (нумерация с 1):");
    for (int i = 0; i < N; i++)
    {
      string? line = Console.ReadLine();
      if (string.IsNullOrWhiteSpace(line))
      {
        Console.WriteLine("Пустой ввод. Повторите");
        i--;
        continue;
      }

      var parts = line.Split(' ', StringSplitOptions.RemoveEmptyEntries);
      if (parts.Length != 2 || !int.TryParse(parts[0], out int car) || !int.TryParse(parts[1], out int seat))
      {
        Console.WriteLine("Ожидается два целых числа");
        i--;
        continue;
      }

      if (car < 1 || car > K || seat < 1 || seat > M)
      {
        Console.WriteLine($"Диапазоны: вагон 1..{K}, место 1..{M}");
        i--;
        continue;
      }

      sold[car - 1, seat - 1] = 1;
    }
  }

  static int SoldInCar(int[,] sold, int carIdx)
  {
    int sum = 0;
    int M = sold.GetLength(1);

    for (int seat = 0; seat < M; seat++)
    {
      sum += sold[carIdx, seat];
    }
    return sum;
  }

  static int FreeInCar(int[,] sold, int carIdx)
  {
    return sold.GetLength(1) - SoldInCar(sold, carIdx);
  }

  static void PrintSummary(int[,] sold)
  {
    int K = sold.GetLength(0), M = sold.GetLength(1), totalFree = 0;
    Console.WriteLine("\nСводка по вагонам:");
    for (int car = 0; car < K; car++)
    {
      int free = FreeInCar(sold, car);
      totalFree += free;
      Console.WriteLine($"Вагон {car + 1}: свободных {free} из {M} — {(free > 0 ? "ЕСТЬ" : "НЕТ")}");
    }
    Console.WriteLine($"Итого свободных мест по поезду: {totalFree}\n");
  }

  static void QueryLoop(int[,] sold)
  {
    int K = sold.GetLength(0);
    Console.WriteLine("Проверка вагона (введите номер вагона, 0 — выход):");
    while (true)
    {
      Console.Write("Вагон #: ");
      string? s = Console.ReadLine();
      if (!int.TryParse(s, out int q))
      {
        Console.WriteLine("Введите целое число");
        continue;
      }
      if (q == 0) break;
      if (q < 1 || q > K)
      {
        Console.WriteLine($"Номер вагона должен быть 1..{K}");
        continue;
      }

      int free = FreeInCar(sold, q - 1);
      Console.WriteLine($"Вагон {q}: свободных мест {free} — {(free > 0 ? "ЕСТЬ" : "НЕТ")}");
    }
  }
}
