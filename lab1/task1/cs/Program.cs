class Program
{
    static bool IsPalindrome(string s) => s == new string([.. s.Reverse()]);
    static void Main()
    {
        while (true)
        {
            Console.Write("Введите четырёхзначное число (или ноль для выхода): ");
            var s = (Console.ReadLine() ?? "").Trim();

            if (s == "0")
            {
                break;
            }

            if (s.Length != 4 || !int.TryParse(s, out _))
            {
                Console.WriteLine("Число не является четырёхзначным");
                continue;
            }

            Console.WriteLine(IsPalindrome(s) ? "Число является палиндромом" : "Число не является палиндромом");
        }
    }
}
