class Program
{
    static void Main()
    {
        const int DAYS = 30;
        const int DEFAULT_SWIMMING_TEMP = 22;

        int swimmingTemp = ReadSwimmingTemp(DEFAULT_SWIMMING_TEMP);
        int[] temps = ReadTemperatures(DAYS);

        int suitableDays = CountSuitableDays(temps, swimmingTemp);
        int maxDecade = FindMaxDecade(temps, swimmingTemp);

        PrintResults(suitableDays, maxDecade);
    }

    static int ReadSwimmingTemp(int defaultTemp)
    {
        Console.Write($"Введите температуру воды, пригодную для купания (по умолчанию {defaultTemp}°C): ");
        string? input = Console.ReadLine()?.Trim();
        return string.IsNullOrEmpty(input) ? defaultTemp : int.Parse(input);
    }

    static int[] ReadTemperatures(int days)
    {
        int[] temps = new int[days];
        Console.WriteLine("\nВведите температуру воды за 30 дней сентября:");
        int filled = 0;
        while (filled < days)
        {
            Console.Write($"День {filled + 1}: ");
            string? input = Console.ReadLine()?.Trim();
            if (string.IsNullOrEmpty(input)) continue;
            temps[filled] = int.Parse(input);
            filled++;
        }
        return temps;
    }

    static int CountSuitableDays(int[] temps, int swimmingTemp)
    {
        int count = 0;
        foreach (int t in temps)
            if (t >= swimmingTemp) count++;
        return count;
    }

    static int FindMaxDecade(int[] temps, int swimmingTemp)
    {
        int[] decadeCount = new int[3];
        for (int i = 0; i < temps.Length; i++)
        {
            if (temps[i] >= swimmingTemp)
            {
                if (i < 10) decadeCount[0]++;
                else if (i < 20) decadeCount[1]++;
                else decadeCount[2]++;
            }
        }

        int maxIndex = 0;
        for (int i = 1; i < 3; i++)
            if (decadeCount[i] > decadeCount[maxIndex])
                maxIndex = i;
        return maxIndex + 1;
    }

    static void PrintResults(int suitableDays, int maxDecade)
    {
        Console.WriteLine($"\nВсего дней, пригодных для купания: {suitableDays}");
        Console.WriteLine($"Больше всего тёплых дней было в {maxDecade} декаде сентября");
    }
}
