using System.Globalization;

class Program
{
  static void Main()
  {
    Console.Write("x (< -1): ");
    double x = double.Parse(Console.ReadLine() ?? "0", CultureInfo.InvariantCulture);
    Console.Write("epsilon (> 0): ");
    double eps = double.Parse(Console.ReadLine() ?? "0", CultureInfo.InvariantCulture);

    if (!(x < -1) || !(eps > 0))
    {
      Console.WriteLine("Некорректные данные: требуется x < -1 и epsilon > 0");
      return;
    }

    double sum = -Math.PI / 2.0;
    double term = -1.0 / x;
    long n = 0;
    const long maxIter = 1_000_000;

    while (Math.Abs(term) >= eps && n < maxIter)
    {
      sum += term;
      term = term * (-(2.0 * n + 1.0) / ((2.0 * n + 3.0) * x * x));
      n++;
    }

    Console.WriteLine($"arctan(x) по ряду = {sum:F12}");
    Console.WriteLine($"Math.Atan(x)      = {Math.Atan(x):F12}");
    Console.WriteLine($"Членов суммировано = {n}, |последний член| < {eps}");
  }
}
