class Program
{
    static void Main()
    {
        const int DAYS = 30;
        const int DEFAULT_SWIMMING_TEMP = 22;
        int[] temps = new int[DAYS];

        Console.Write($"Введите температуру воды, пригодную для купания (по умолчанию {DEFAULT_SWIMMING_TEMP}°C): ");
        string? inputTemp = Console.ReadLine();
        if (string.IsNullOrEmpty(inputTemp))
            inputTemp = "";

        int swimmingTemp = string.IsNullOrEmpty(inputTemp.Trim()) ? DEFAULT_SWIMMING_TEMP : int.Parse(inputTemp);

        Console.WriteLine("\nВведите температуру воды за 30 дней сентября:");
        int filled = 0;
        while (filled < DAYS)
        {
            Console.Write($"День {filled + 1}: ");
            string? inputDayTemp = Console.ReadLine();
            if (string.IsNullOrEmpty(inputDayTemp))
                continue;
            inputDayTemp = inputDayTemp.Trim();
            if (string.IsNullOrEmpty(inputDayTemp))
                continue;
            temps[filled] = int.Parse(inputDayTemp);
            filled++;
        }

        int suitableDays = 0;
        int[] decadeCount = new int[3]; // 0:1–10, 1:11–20, 2:21–30

        for (int i = 0; i < DAYS; i++)
        {
            if (temps[i] >= swimmingTemp)
            {
                suitableDays++;
                if (i < 10) decadeCount[0]++;
                else if (i < 20) decadeCount[1]++;
                else decadeCount[2]++;
            }
        }

        Console.WriteLine($"\nВсего дней, пригодных для купания: {suitableDays}");

        int maxIndex = 0;
        for (int i = 1; i < 3; i++)
        {
            if (decadeCount[i] > decadeCount[maxIndex])
                maxIndex = i;
        }

        Console.WriteLine($"Больше всего тёплых дней было в {maxIndex + 1} декаде сентября");
    }
}
